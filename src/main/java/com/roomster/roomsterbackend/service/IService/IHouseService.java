package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.entity.House;
import org.springframework.http.ResponseEntity;

public interface IHouseService {

	ResponseEntity<?> getAllHouses();

	ResponseEntity<?> getHouseById(String id);

	ResponseEntity<?> createHouse(House house);

	ResponseEntity<?> updateHouse(String id, House house);

	ResponseEntity<?> deleteHouse(String id);
	
	

}
