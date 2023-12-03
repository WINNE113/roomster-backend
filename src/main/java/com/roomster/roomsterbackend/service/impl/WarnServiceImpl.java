package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.entity.District;
import com.roomster.roomsterbackend.entity.Ward;
import com.roomster.roomsterbackend.repository.WarnRepository;
import com.roomster.roomsterbackend.service.IService.IWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WarnServiceImpl implements IWarnService {
    @Autowired
    WarnRepository warnRepository;

    @Override
    public List<Ward> findAll() {
        return warnRepository.findAll();
    }

    @Override
    public Ward findById(Long id) {
        Optional<Ward> ward = warnRepository.findById(id);
        if(ward.isPresent())
            return ward.get();
        return null;
    }
}
