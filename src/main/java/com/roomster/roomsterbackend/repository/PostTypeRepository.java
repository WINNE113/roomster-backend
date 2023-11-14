package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.dto.PostTypeDto;
import com.roomster.roomsterbackend.entity.PostTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PostTypeRepository extends JpaRepository<PostTypeEntity, Long> {
    PostTypeEntity getPostEntityByName(String postTypeName);
    PostTypeEntity getPostEntityById(Long id);
}
