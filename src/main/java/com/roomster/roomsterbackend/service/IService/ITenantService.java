package com.roomster.roomsterbackend.service.IService;

import org.springframework.http.ResponseEntity;

import com.roomster.roomsterbackend.entity.Tenant;

import java.util.List;

public interface ITenantService {

	
	ResponseEntity<?> getAllTenant();

	ResponseEntity<?> getGuestById(String id);
	
	ResponseEntity<?> getTenantByRoomId(String id);

	ResponseEntity<?> createTenant(Tenant tenant);

	ResponseEntity<?> updateTenant(String id, Tenant tenant);

	ResponseEntity<?> deleteTenant(List<String> tenantIds);

	ResponseEntity<?> moveTenant(String id, List<String> tenantIds);
}
