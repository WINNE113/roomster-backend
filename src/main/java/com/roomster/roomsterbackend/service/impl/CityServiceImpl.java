package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.entity.City;
import com.roomster.roomsterbackend.repository.CityRepository;
import com.roomster.roomsterbackend.service.IService.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements ICityService {
    @Autowired
    CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        if(city.isPresent())
            return city.get();
        return null;
    }
}
