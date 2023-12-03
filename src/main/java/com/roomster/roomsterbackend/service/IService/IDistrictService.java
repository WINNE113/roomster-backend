package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.entity.District;

import java.util.List;

public interface IDistrictService {
    List<District> findAll();

    District findById(Long id);
}
