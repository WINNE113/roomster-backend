package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    @Query("SELECT d FROM District d WHERE d.cityId = ?1")
    public List<District> getAllDistrictByCityId(Long cityId);

}
