package com.roomster.roomsterbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.entity.RoomService;
import com.roomster.roomsterbackend.entity.ServiceHouse;
import com.roomster.roomsterbackend.repository.RoomRepository;
import com.roomster.roomsterbackend.repository.RoomServiceRepository;
import com.roomster.roomsterbackend.repository.ServiceRepository;
import com.roomster.roomsterbackend.service.IService.IServiceService;
import com.roomster.roomsterbackend.util.message.MessageUtil;
import com.roomster.roomsterbackend.util.validator.ValidatorUtils;

import jakarta.transaction.Transactional;

@Service
public class ServiceServiceImpl implements IServiceService {

	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	RoomServiceRepository roomServiceRepository;

	@Override
	public ResponseEntity<?> findAll() {
		ResponseEntity<?> responseEntity;
		try {
			List<ServiceHouse> serviceHouseList = serviceRepository.findAll();
			responseEntity = new ResponseEntity<>(serviceHouseList, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> getServiceHouseById(String id) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<ServiceHouse> serviceHouse = this.serviceRepository.findById(idL);
				if (serviceHouse.isPresent()) {
					responseEntity = new ResponseEntity<>(serviceHouse.get(), HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SERVICE_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> createServiceHouse(ServiceHouse serviceHouse) {
		ResponseEntity<?> responseEntity;
		try {
			serviceHouse = serviceRepository.save(serviceHouse);
			responseEntity = new ResponseEntity<>(serviceHouse, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> updateServiceHouse(String id, ServiceHouse serviceHouse) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<ServiceHouse> existingHouseOptional = serviceRepository.findById(idL);
				if (existingHouseOptional.isPresent()) {
					ServiceHouse existingServiceHouse = existingHouseOptional.get();
					existingServiceHouse.setServiceName(serviceHouse.getServiceName());
					existingServiceHouse.setServicePrice(serviceHouse.getServicePrice());
					existingServiceHouse = serviceRepository.save(existingServiceHouse);
					responseEntity = new ResponseEntity<>(existingServiceHouse, HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ORDER_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> deleteServiceHouse(String id) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<ServiceHouse> serviceHouse = this.serviceRepository.findById(idL);
				if (serviceHouse.isPresent()) {
					serviceRepository.deleteById(idL);
					responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_DELETE_SUCCESS), HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ORDER_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Transactional
	@Override
	public ResponseEntity<?> updateServiceHouseByRoomId(String id, List<String> listServiceIds) {
		ResponseEntity<?> responseEntity;
		
		try {
			Optional<InforRoomEntity> room = roomRepository.findById(Long.parseLong(id));
			InforRoomEntity roomService = room.get();
			if(roomService != null) {
				for (RoomService service : roomService.getServices()) {
					roomServiceRepository.deleteById(service.getRoomService());
					
				}
				
				for (String serviceId : listServiceIds) {
					Long serviceRoomId = Long.parseLong(id);
					if(serviceRepository.existsById(serviceRoomId)){
						RoomService roomServiceUpdate = new RoomService();
						roomServiceUpdate.setRoomId(Long.parseLong(id));
						roomServiceUpdate.setServiceId(Long.parseLong(serviceId));
						roomServiceRepository.save(roomServiceUpdate);
						
					}
					else {
						return new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SERVICE_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
						
					}
				}
				responseEntity = new ResponseEntity<>(HttpStatus.OK);
			}
			else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ROOM_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
