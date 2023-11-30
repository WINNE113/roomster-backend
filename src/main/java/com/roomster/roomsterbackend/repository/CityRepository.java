package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.entity.City;
import com.roomster.roomsterbackend.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
