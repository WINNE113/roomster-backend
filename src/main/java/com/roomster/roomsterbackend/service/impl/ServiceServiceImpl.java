package com.roomster.roomsterbackend.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.roomster.roomsterbackend.entity.OrderEntity;
import com.roomster.roomsterbackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.entity.RoomServiceEntity;
import com.roomster.roomsterbackend.entity.ServiceHouseEntity;
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

	@Autowired
	OrderRepository orderRepository;

	@Override
	public ResponseEntity<?> findAll() {
		ResponseEntity<?> responseEntity;
		try {
			List<ServiceHouseEntity> serviceHouseList = serviceRepository.findAll();
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
				Optional<ServiceHouseEntity> serviceHouse = this.serviceRepository.findById(idL);
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

	private boolean isValidService(ServiceHouseEntity service) {
		Long count = serviceRepository.countByNameAndDifferentId(service.getServiceName(), service.getServiceId());
		return count == 0;
	}


	@Override
	public ResponseEntity<?> createServiceHouse(ServiceHouseEntity serviceHouse) {
		ResponseEntity<?> responseEntity;
		try {
			if(isValidService(serviceHouse)){
				serviceRepository.save(serviceHouse);
				responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_ADD_SUCCESS), HttpStatus.OK);
			}
			else{
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SERVICE_EXISTS), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> updateServiceHouse(String id, ServiceHouseEntity serviceHouse) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<ServiceHouseEntity> existingHouseOptional = serviceRepository.findById(idL);
				if (existingHouseOptional.isPresent()) {
					if(isValidService(serviceHouse)){
						ServiceHouseEntity existingServiceHouse = existingHouseOptional.get();
						existingServiceHouse.setServiceName(serviceHouse.getServiceName());
						existingServiceHouse.setServicePrice(serviceHouse.getServicePrice());
						serviceRepository.save(existingServiceHouse);
						responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_UPDATE_SUCCESS), HttpStatus.OK);
					}
					else{
						responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SERVICE_EXISTS), HttpStatus.BAD_REQUEST);
					}
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ORDER_NOT_FOUND), HttpStatus.BAD_REQUEST);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> deleteServiceHouse(List<String> listServices) {
		ResponseEntity<?> responseEntity;
		try {
			if (listServices.size() > 0) {
				for (String serviceId : listServices) {
					Long serviceRoomId = Long.parseLong(serviceId);
					if (serviceRepository.existsById(serviceRoomId)) {
						serviceRepository.deleteById(serviceRoomId);
					}
					else {
						return new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SERVICE_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_DELETE_SUCCESS), HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_DELETE_SUCCESS), HttpStatus.OK);
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
			BigDecimal priceService = BigDecimal.ZERO;
			if(room.isPresent()) {
				InforRoomEntity roomService = room.get();
				for (RoomServiceEntity service : roomService.getServices()) {
					roomServiceRepository.deleteById(service.getRoomService());
				}
				for (String serviceId : listServiceIds) {
					Long serviceRoomId = Long.parseLong(serviceId);
					if(serviceRepository.existsById(serviceRoomId)){
						RoomServiceEntity roomServiceUpdate = new RoomServiceEntity();
						roomServiceUpdate.setRoomId(Long.parseLong(id));
						roomServiceUpdate.setServiceId(Long.parseLong(serviceId));
						roomServiceRepository.save(roomServiceUpdate);
					}
					else {
						return new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SERVICE_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				// update service price in order
				Optional<OrderEntity> orderOptional =	orderRepository.findOrderForRoomInCurrentMonth(roomService.getId());
				if(orderOptional.isPresent()) {
					OrderEntity order = orderOptional.get();
					for (RoomServiceEntity roomServicePrice : roomService.getServices()) {
						priceService = priceService.add(roomServicePrice.getServiceHouse().getServicePrice());
					}
					order.setService(priceService);
					orderRepository.save(order);
				}
				responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_UPDATE_SUCCESS), HttpStatus.OK);
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
