package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarnRepository extends JpaRepository<Ward, Long> {

    @Query("SELECT w FROM Ward w WHERE w.districtId = ?1")
    public List<Ward> getAllWarnByDistrictId(Long districtId);
}
