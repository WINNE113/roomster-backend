package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> getPostEntityByAuthorId(Pageable pageable, Long id);
}
