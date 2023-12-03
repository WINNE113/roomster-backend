package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.entity.InforRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<InforRoomEntity, Long> {
    @Override
    List<InforRoomEntity> findAll();
}
