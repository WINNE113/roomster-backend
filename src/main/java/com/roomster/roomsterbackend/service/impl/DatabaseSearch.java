package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.repository.PostTypeRepository;
import com.roomster.roomsterbackend.service.IService.IDatabaseSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class DatabaseSearch implements IDatabaseSearch {
    @Autowired
    private PostTypeRepository repository;
    @Override
    public List<PostDto> searchFilter(LinkedHashMap<String, Object> map) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/trouytin_db";
        String username = "root";
        String password = "huuthang";
        String tableName = "posts";
        String joinTable = "infor_rooms";

        if(map.containsKey("post_type")){
            Long postTypeId = repository.getPostEntityByName((String) map.get("post_type")).getId();
            map.put("post_type_id", postTypeId);
            map.remove("post_type");
        }

        final Connection connection = DriverManager.getConnection(url, username, password);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(tableName)
                .append(" inner join ").append(joinTable)
                .append(" on posts.room_id = infor_rooms.id ").append(" where ");
        int count = 0;
        for(String key : map.keySet()){
            if(count > 0){
                sql.append(" AND ");
            }
            switch (key) {
                case "price" -> sql.append("price BETWEEN ? AND ?");
                case "acreage" -> sql.append("acreage BETWEEN ? AND ?");
                default -> {
                    if (map.get(key) instanceof String) {
                        sql.append(key).append(" LIKE ?");
                    } else if (map.get(key) instanceof Number) {
                        sql.append(key).append(" = ?");
                    }
                }
            }
            count++;
        }

        System.out.println(sql);

        // Prepare the statement and pass the search parameter values
        PreparedStatement stmt = connection.prepareStatement(sql.toString());
        int parameterIndex = 0;
        for (String key : map.keySet()) {
            if (map.get(key) instanceof int[]) {
                int[] range = (int[])map.get(key);
                if(range.length >= 2){
                    stmt.setObject(parameterIndex + 1, range[0]);
                    stmt.setObject(parameterIndex + 2, range[1]);
                }
                parameterIndex += 2;
            } else {
                if (map.get(key) instanceof String) {
                    stmt.setString(parameterIndex + 1, "%" + map.get(key) + "%");
                } else if (map.get(key) instanceof Number) {
                    stmt.setObject(parameterIndex + 1, map.get(key));
                }
                parameterIndex++;
            }
        }
        System.out.println(stmt);

        List<PostDto> postDTOs = new ArrayList<>();
        try(ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PostDto postDTO = new PostDto(
                        Long.valueOf(rs.getString(1)),
                        rs.getString("title"),
                        rs.getString("address"),
                        rs.getString("description"),
                        rs.getString("object"),
                        rs.getString("convenient"),
                        rs.getString("surroundings"),
                        repository.getPostEntityById(rs.getLong("post_type_id")).getName(),
                        Long.valueOf(rs.getString("author_id")));
                postDTOs.add(postDTO);
            }
        }

        return postDTOs;
    }
}
