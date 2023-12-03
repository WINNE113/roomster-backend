package com.roomster.roomsterbackend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roomster.roomsterbackend.entity.Tenant;
import com.roomster.roomsterbackend.service.IService.ITenantService;

@RestController
@CrossOrigin("*")
@RequestMapping("/room-master/tenant")
public class TenantController {

	@Autowired
    private ITenantService tenantService;

    @GetMapping
    public ResponseEntity<?> getAllTenants() {
        return tenantService.getAllTenant();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTenantById(@PathVariable String id) {
        return tenantService.getGuestById(id);
    }

    @GetMapping("/house/{id}")
    public ResponseEntity<?> getTenantsByHouseId(@PathVariable String id) {
        return tenantService.getTenantByRoomId(id);
    }

    @PostMapping
    public ResponseEntity<?> createTenant(@RequestBody Tenant tenant) {
        return tenantService.createTenant(tenant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTenant(@PathVariable String id, @RequestBody Tenant tenant) {
        return tenantService.updateTenant(id, tenant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTenant(@PathVariable String id) {
        return tenantService.deleteTenant(id);
    }
}
