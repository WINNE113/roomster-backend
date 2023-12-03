package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.entity.City;

import java.util.List;

public interface ICityService {
    List<City> findAll();

    City findById(Long id);
}
