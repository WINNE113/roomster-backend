package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.dto.PostDetailDto;
import com.roomster.roomsterbackend.dto.PostDtoWithRating;
import com.roomster.roomsterbackend.dto.PostImageDto;
import com.roomster.roomsterbackend.dto.ProvinceDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> getPostEntityByAuthorId(Pageable pageable, Long id);

    @Query(value = "Select p.id, p.title, p.address, p.created_date as createdDate, i.price, p.is_deleted as isDeleted, max(pimg.image_url_list) as image, AVG(r.star_point) as averageRating\n" +
            "from posts p \n" +
            "left join ratings r on p.id = r.post_id \n" +
            "inner join infor_rooms i on p.room_id = i.id\n" +
            "inner join post_entity_image_url_list pimg on pimg.post_entity_id = p.id\n" +
            "group by p.id\n" +
            "Order by averageRating desc", nativeQuery = true)
    List<PostDtoWithRating> getPostByRating(Pageable pageable);

    @Query(value = "SELECT\n" +
            "    TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(address, ',', -1), ',', 1)) AS provinceName,\n" +
            "    COUNT(*) AS totalPosts\n" +
            "FROM\n" +
            "    posts\n" +
            "where posts.is_deleted = false\n" +
            "GROUP BY\n" +
            "    provinceName\n" +
            "ORDER BY\n" +
            "    totalPosts DESC", nativeQuery = true)
    List<ProvinceDto> getTopOfProvince(Pageable pageable);

    @Query(value = "select p.id, p.title, p.address, p.description, p.object, p.convenient, p.surroundings, p.post_type_id as postType, p.created_by as createdBy, p.created_date as createdDate, p.rotation, ir.acreage, ir.electricity_price as electricityPrice, ir.price, ir.staymax, ir.water_price as waterPrice\n" +
            "from posts p\n" +
            "left join infor_rooms ir\n" +
            "on p.room_id = ir.id\n" +
            "where p.id =:postId\n" +
            "group by p.id, p.title, p.address, p.description, p.object, p.convenient, p.surroundings, p.post_type_id, p.created_by, p.created_date, p.rotation, ir.acreage, ir.electricity_price, ir.price, ir.staymax, ir.water_price", nativeQuery = true)
    Optional<PostDetailDto> getPostDetail(@Param("postId") Long postId);


    @Query(value = "select img.image_url_list as image from post_entity_image_url_list img where img.post_entity_id =:postId", nativeQuery = true)
    List<PostImageDto> getPostImages(@Param("postId") Long postId);
}
