package com.roomster.roomsterbackend.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomster.roomsterbackend.entity.Tenant;
import com.roomster.roomsterbackend.repository.TenantRepository;
import com.roomster.roomsterbackend.service.IService.ITenantService;

@Service
public class TenantServiceImpl implements ITenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public ResponseEntity<?> getAllTenant() {
        try {
            List<Tenant> tenants = tenantRepository.findAll();
            return ResponseEntity.ok(tenants);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving tenants", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getGuestById(String id) {
        try {
            Tenant tenant = tenantRepository.findById(Long.parseLong(id)).orElse(null);
            if (tenant != null) {
                return ResponseEntity.ok(tenant);
            } else {
                return new ResponseEntity<>("Tenant not found", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid tenant ID format", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getTenantByRoomId(String id) {
        try {
            List<Tenant> tenants = tenantRepository.findByRoomId(Long.parseLong(id));
            return ResponseEntity.ok(tenants);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid house ID format", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> createTenant(Tenant tenant) {
        try {
            Tenant createdTenant = tenantRepository.save(tenant);
            return new ResponseEntity<>(createdTenant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating tenant", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> updateTenant(String id, Tenant tenant) {
        try {
            Tenant updatedTenant = tenantRepository.findById(Long.parseLong(id)).map(existingTenant -> {
                // Update existingTenant properties based on the tenant input
                // For simplicity, I'm returning existingTenant as is. Replace it with your actual logic.
                return existingTenant;
            }).orElse(null);

            if (updatedTenant != null) {
                return ResponseEntity.ok(updatedTenant);
            } else {
                return new ResponseEntity<>("Tenant not found", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid tenant ID format", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteTenant(String id) {
        try {
            tenantRepository.deleteById(Long.parseLong(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid tenant ID format", HttpStatus.BAD_REQUEST);
        }
    }
}
