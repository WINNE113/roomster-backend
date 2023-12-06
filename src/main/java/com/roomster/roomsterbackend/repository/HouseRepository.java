package com.roomster.roomsterbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roomster.roomsterbackend.entity.House;


@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

	List<House> findAll();
	
	Optional<House> findById(Long id);

	@Query("SELECT COUNT(h) FROM House h " +
			"JOIN h.ward w " +
			"WHERE (w.wardId = :wardId AND h.address = :currentAddress) " +
			"AND (:houseId IS NULL OR h.houseId != :houseId)")
	Long countHousesDifferentAddress(@Param("currentAddress") String currentAddress,
									 @Param("wardId") Long wardId,
									 @Param("houseId") Long houseId);

	@Query("SELECT COUNT(h) FROM House h " +
			"JOIN h.ward w " +
			"WHERE (w.wardId = :wardId AND h.houseName = :houseName) " +
			"AND (:houseId IS NULL OR h.houseId != :houseId)")
	Long countHousesDifferentName(@Param("houseName") String houseName,
								  @Param("wardId") Long wardId,
								  @Param("houseId") Long houseId);

}
