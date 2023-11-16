package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.dto.PostDtoWithRating;
import com.roomster.roomsterbackend.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> getPostEntityByAuthorId(Pageable pageable, Long id);

    @Query("""
    select new com.roomster.roomsterbackend.dto.PostDtoWithRating(p.id, p.title, p.address, p.createdBy, p.createdDate, i.price, p.isDeleted, AVG(r.starPoint))
    from PostEntity p
    left join RatingEntity r on p.id = r.postId
    inner join InforRoomEntity i on p.roomId.id = i.id
    group by p.id
    order by AVG(r.starPoint) desc
""")
    List<PostDtoWithRating> getPostByRating(Pageable pageable);
}
