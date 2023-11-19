package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    List<RatingEntity> getRatingEntitiesByPostId(Long postId);
}
