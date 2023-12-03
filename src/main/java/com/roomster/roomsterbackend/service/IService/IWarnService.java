package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.entity.Ward;

import java.util.List;

public interface IWarnService {
    List<Ward> findAll();

    Ward findById(Long id);
}
