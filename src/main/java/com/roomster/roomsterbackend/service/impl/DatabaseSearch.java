package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.SearchResult;
import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.mapper.UserMapper;
import com.roomster.roomsterbackend.repository.PostTypeRepository;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IService.IDatabaseSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class DatabaseSearch implements IDatabaseSearch {
    @Autowired
    private PostTypeRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public SearchResult searchFilter(Pageable pageable, LinkedHashMap<String, Object> map) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/trouytin_db";
        String username = "root";
        String password = "huuthang";
        String tableName = "posts";
        String joinTable = "infor_rooms";

        if (map.containsKey("post_type")) {
            Long postTypeId = repository.getPostEntityByName((String) map.get("post_type")).getId();
            map.put("post_type_id", postTypeId);
            map.remove("post_type");
        }

        final Connection connection = DriverManager.getConnection(url, username, password);
        StringBuilder filterQuery = new StringBuilder();
        StringBuilder totalResultQuery = new StringBuilder();
        totalResultQuery.append("SELECT count(*) as total FROM ").append(tableName).append(" inner join ").append(joinTable).append(" on posts.room_id = infor_rooms.id ").append(" where ");
        filterQuery.append("SELECT * FROM ").append(tableName).append(" inner join ").append(joinTable).append(" on posts.room_id = infor_rooms.id ").append(" where ");
        int count = 0;
        for (String key : map.keySet()) {
            if (count > 0) {
                filterQuery.append(" AND ");
                totalResultQuery.append(" AND ");
            }
            switch (key) {
                case "price" -> {
                    filterQuery.append("price BETWEEN ? AND ?");
                    totalResultQuery.append("price BETWEEN ? AND ?");
                }
                case "acreage" -> {
                    filterQuery.append("acreage BETWEEN ? AND ?");
                    totalResultQuery.append("acreage BETWEEN ? AND ?");
                }
                default -> {
                    if (map.get(key) instanceof String) {
                        filterQuery.append(key).append(" LIKE ?");
                        totalResultQuery.append(key).append(" LIKE ?");
                    } else if (map.get(key) instanceof Number) {
                        filterQuery.append(key).append(" = ?");
                        totalResultQuery.append(key).append(" = ?");
                    }
                }
            }
            count++;
        }
        // Use Pageable to determine limit and offset
        filterQuery.append(" LIMIT ? OFFSET ?");

        System.out.println(filterQuery);
        System.out.println(totalResultQuery);

        // Prepare the statement and pass the search parameter values
        PreparedStatement stmtToFilter = connection.prepareStatement(filterQuery.toString());
        PreparedStatement stmtToTotalResult = connection.prepareStatement(totalResultQuery.toString());
        int parameterIndex = 0;
        for (String key : map.keySet()) {
            if (map.get(key) instanceof int[]) {
                int[] range = (int[]) map.get(key);
                if (range.length >= 2) {
                    stmtToFilter.setObject(parameterIndex + 1, range[0]);
                    stmtToFilter.setObject(parameterIndex + 2, range[1]);

                    stmtToTotalResult.setObject(parameterIndex + 1, range[0]);
                    stmtToTotalResult.setObject(parameterIndex + 2, range[1]);
                }
                parameterIndex += 2;
            } else {
                if (map.get(key) instanceof String) {
                    stmtToFilter.setString(parameterIndex + 1, "%" + map.get(key) + "%");

                    stmtToTotalResult.setString(parameterIndex + 1, "%" + map.get(key) + "%");
                } else if (map.get(key) instanceof Number) {
                    stmtToFilter.setObject(parameterIndex + 1, map.get(key));

                    stmtToTotalResult.setObject(parameterIndex + 1, map.get(key));
                }
                parameterIndex++;
            }
        }

        stmtToFilter.setInt(parameterIndex + 1, pageable.getPageSize());
        stmtToFilter.setLong(parameterIndex + 2, pageable.getOffset());
        System.out.println(stmtToFilter);
        System.out.println(stmtToTotalResult);

//        getTotalResult
        int totalCount = 0;
        try (ResultSet countResultSet = stmtToTotalResult.executeQuery()){
            if (countResultSet.next()) {
                totalCount = countResultSet.getInt(1);
            }
        }

        System.out.println(totalCount);

        List<PostDto> postDTOs = new ArrayList<>();
        try (ResultSet rs = stmtToFilter.executeQuery()) {
            while (rs.next()) {
                PostDto postDTO = new PostDto();
                postDTO.setPostId(Long.valueOf(rs.getString("id")));
                postDTO.setTitle(rs.getString("title"));
                postDTO.setAddress(rs.getString("address"));
                postDTO.setDescription(rs.getString("description"));
                postDTO.setConvenient(rs.getString("convenient"));
                postDTO.setObject(rs.getString("object"));
                postDTO.setSurroundings(rs.getString("surroundings"));
                if (rs.getLong("post_type_id") != 0 && rs.getLong("author_id") != 0) {
                    postDTO.setPost_type(repository.getPostEntityById(rs.getLong("post_type_id")).getName());
                    Optional<UserEntity> user = userRepository.getUserEntityByUserId(rs.getLong("author_id"));
                    if(user.isPresent()){
                        postDTO.setAuthorId(userMapper.entityToDto(user.get()));
                    }
                }
                postDTOs.add(postDTO);
            }
        }
        SearchResult searchResult = new SearchResult();
        searchResult.setTotal(totalCount);
        searchResult.setData(postDTOs);
        return searchResult;
    }
}
