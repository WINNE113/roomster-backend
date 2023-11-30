package com.roomster.roomsterbackend.service.impl;

import java.util.List;
import java.util.Optional;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.util.message.MessageUtil;
import com.roomster.roomsterbackend.util.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomster.roomsterbackend.entity.House;
import com.roomster.roomsterbackend.repository.HouseRepository;
import com.roomster.roomsterbackend.service.IService.IHouseService;

@Service
public class HouseServiceImpl implements IHouseService {

	@Autowired
	HouseRepository houseRepository;

	@Override
	public ResponseEntity<?> getAllHouses() {
		ResponseEntity<?> responseEntity;
		try {
			List<House> inforHouseEntityList = houseRepository.findAll();
			responseEntity = new ResponseEntity<>(inforHouseEntityList, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> getHouseById(String id) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<House> house = this.houseRepository.findById(idL);
				if (house.isPresent()) {
					responseEntity = new ResponseEntity<>(house.get(), HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_HOUSE_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<?> createHouse(House house) {
		ResponseEntity<?> responseEntity;
		try {
			house = houseRepository.save(house);
			responseEntity = new ResponseEntity<>(house, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@Override
	public ResponseEntity<?> updateHouse(String id, House updatedHouse) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<House> existingHouseOptional = houseRepository.findById(idL);
				if (existingHouseOptional.isPresent()) {
					House existingHouse = existingHouseOptional.get();

					existingHouse.setHouseName(updatedHouse.getHouseName());
					existingHouse.setWard(updatedHouse.getWard());
					existingHouse.setAddress(updatedHouse.getAddress());
					existingHouse = houseRepository.save(existingHouse);
					responseEntity = new ResponseEntity<>(existingHouse, HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_HOUSE_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<?> deleteHouse(String id) {
		ResponseEntity<?> responseEntity;
		try {
			if (ValidatorUtils.isNumber(id)) {
				Long idL = Long.parseLong(id);
				Optional<House> house = this.houseRepository.findById(idL);
				if (house.isPresent()) {
					this.houseRepository.deleteById(idL);
					responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_DELETE_SUCCESS), HttpStatus.OK);
				} else {
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_HOUSE_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

}
