package com.roomster.roomsterbackend.service.IService;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.roomster.roomsterbackend.entity.ServiceHouse;

public interface IServiceService {

	ResponseEntity<?> findAll();

	ResponseEntity<?>  getServiceHouseById(String id);

	ResponseEntity<?>  createServiceHouse(ServiceHouse serviceHouse);

	ResponseEntity<?>  updateServiceHouse(String id, ServiceHouse serviceHouse);

	ResponseEntity<?>  deleteServiceHouse(List<String> listServices);
	
	ResponseEntity<?>  updateServiceHouseByRoomId(String id, List <String> listServiceIds);
	
	
	

}
