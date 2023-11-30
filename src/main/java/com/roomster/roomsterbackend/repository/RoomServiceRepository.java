package com.roomster.roomsterbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roomster.roomsterbackend.entity.RoomService;

@Repository
public interface RoomServiceRepository extends JpaRepository<RoomService, Long> {

	
	
}
