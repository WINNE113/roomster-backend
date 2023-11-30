package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.entity.City;
import com.roomster.roomsterbackend.entity.District;
import com.roomster.roomsterbackend.repository.DistrictRepository;
import com.roomster.roomsterbackend.service.IService.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    DistrictRepository districtRepository;

    @Override
    public List<District> findAll() {
        return districtRepository.findAll();
    }

    @Override
    public District findById(Long id) {
        Optional<District> district = districtRepository.findById(id);
        if(district.isPresent())
            return district.get();
        return null;
    }
}
