package com.roomster.roomsterbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roomster.roomsterbackend.entity.RoomService;
import com.roomster.roomsterbackend.entity.ServiceHouse;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceHouse, Long> {
	
	 List<ServiceHouse> findAll() ;
	 
	 Optional<ServiceHouse> findById(Long id);

	void save(RoomService roomServiceUpdate);

	@Query("SELECT COUNT(s) FROM ServiceHouse s " +
			"WHERE s.serviceName = :serviceName " +
			"AND (:serviceId IS NULL OR s.serviceId != :serviceId)")
	Long countByNameAndDifferentId(@Param("serviceName") String serviceName, @Param("serviceId") Long serviceId);

}
