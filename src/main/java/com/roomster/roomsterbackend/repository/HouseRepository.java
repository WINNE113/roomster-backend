package com.roomster.roomsterbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roomster.roomsterbackend.entity.House;


@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

	List<House> findAll();
	
	Optional<House> findById(Long id);

}
