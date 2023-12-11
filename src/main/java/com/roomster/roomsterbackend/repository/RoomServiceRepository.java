package com.roomster.roomsterbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roomster.roomsterbackend.entity.RoomServiceEntity;

@Repository
public interface RoomServiceRepository extends JpaRepository<RoomServiceEntity, Long> {

	
	
}
