package com.roomster.roomsterbackend.controller.admin;

import java.util.List;

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

import com.roomster.roomsterbackend.entity.ServiceHouse;
import com.roomster.roomsterbackend.service.IService.IServiceService;


@RestController
@CrossOrigin("*")
@RequestMapping("/room-master/serviceHouse")
public class ServiceController {

	@Autowired
    IServiceService serviceHouse;

	@GetMapping("")
	public ResponseEntity<?> getAllServiceHouse() {
		return serviceHouse.findAll();
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> getServiceHouseById(@PathVariable String id) {
        return serviceHouse.getServiceHouseById(id);
    }

    @PostMapping
    public ResponseEntity<?> createServiceHouse(@RequestBody ServiceHouse ServiceHouse) {
        return serviceHouse.createServiceHouse(ServiceHouse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateServiceHouse(@PathVariable String id, @RequestBody ServiceHouse ServiceHouse) {
        return serviceHouse.updateServiceHouse(id, ServiceHouse);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteServiceHouse(@RequestBody List<String> listServices) {
        return serviceHouse.deleteServiceHouse(listServices);
    }
    
    @PutMapping("/room-service/{id}")
    public ResponseEntity<?> updateRoomServiceHouseByRoomId(@PathVariable String id, @RequestBody List<String> listServices) {
        System.out.println("HERE WE GO");
        System.out.println("ID: " + id);
        System.out.println("List Service IDs: " + listServices);
        return serviceHouse.updateServiceHouseByRoomId(id, listServices);
    }
}
