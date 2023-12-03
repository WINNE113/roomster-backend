package com.roomster.roomsterbackend.service.IService;

import org.springframework.http.ResponseEntity;

import com.roomster.roomsterbackend.entity.Tenant;

public interface ITenantService {

	
	ResponseEntity<?> getAllTenant();

	ResponseEntity<?> getGuestById(String id);
	
	ResponseEntity<?> getTenantByRoomId(String id);

	ResponseEntity<?> createTenant(Tenant tenant);

	ResponseEntity<?> updateTenant(String id, Tenant tenant);

	ResponseEntity<?> deleteTenant(String id);
}
