package com.roomster.roomsterbackend.service.impl.ultiManger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.roomster.roomsterbackend.base.BaseResponse;
import com.roomster.roomsterbackend.dto.admin.HouseDto;
import com.roomster.roomsterbackend.dto.inforRoom.InforRoomStatusDto;
import com.roomster.roomsterbackend.entity.HouseEntity;
import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.mapper.HouseMapper;
import com.roomster.roomsterbackend.repository.RoomRepository;
import com.roomster.roomsterbackend.util.message.MessageUtil;
import com.roomster.roomsterbackend.util.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.roomster.roomsterbackend.repository.HouseRepository;
import com.roomster.roomsterbackend.service.IService.ultiManager.IHouseService;

@Service
public class HouseServiceImpl implements IHouseService {

	@Autowired
	HouseRepository houseRepository;
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	HouseMapper houseMapper;

	@Override
	public ResponseEntity<?> getAllHouses(String price, String acreage, String stayMax, String status) {
		ResponseEntity<?> responseEntity;
		try {
			Long priceL = Long.parseLong(price);
			Double acreageD = Double.parseDouble(acreage);
			Integer stayMaxI = Integer.parseInt(stayMax);

			Integer statusL;
			if (status != null) {
				statusL = Integer.parseInt(status);
			} else {
				statusL = null;
			}

			List<HouseEntity> inforHouseEntityList = houseRepository.findAll();
			for (HouseEntity house : inforHouseEntityList) {
				house.getRooms().sort(Comparator.comparing(InforRoomEntity::getNumberRoom));
				house.setRooms(house.getRooms().stream().filter(room -> room.getPrice().compareTo(
						BigDecimal.valueOf(priceL)) >= 0
						&& room.getAcreage() >= acreageD
						&& (stayMaxI == 0 || room.getStayMax() == stayMaxI)
						&& (status == null || room.getEmptyRoom() == statusL)).toList());
			}
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
					Optional<HouseEntity> houseOptional = this.houseRepository.findById(idL);
					if (houseOptional.isPresent()) {
						HouseDto house = houseMapper.entityToDTO(houseOptional.get());
						house.setDistrictId(houseOptional.get().getWard().getDistrictId());
						house.setCityId(houseOptional.get().getWard().getDistrict().getCityId());
						responseEntity = new ResponseEntity<>(house, HttpStatus.OK);
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

		private boolean isHouseAddressValid(HouseDto house) {
			Long countAddress = this.houseRepository.countHousesDifferentAddress(house.getAddress(), house.getWarnId(), house.getHouseId());
			Long countName = this.houseRepository.countHousesDifferentName(house.getHouseName(), house.getWarnId(), house.getHouseId());
			return countAddress == 0 && countName == 0;
		}

		@Override
		public ResponseEntity<?> createHouse(HouseDto house) {
			ResponseEntity<?> responseEntity;
			try {
				if(isHouseAddressValid(house)){
					house = houseMapper.entityToDTO(houseRepository.save(houseMapper.dtoToEntity(house)));
					responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_ADD_SUCCESS), HttpStatus.OK);
				} else{
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_HOUSE_EXISTS), HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return responseEntity;
		}

		@Override
		public ResponseEntity<?> updateHouse(String id, HouseDto updatedHouse) {
			ResponseEntity<?> responseEntity;
			try {
				if(isHouseAddressValid(updatedHouse)){
					if (ValidatorUtils.isNumber(id)) {
						Long idL = Long.parseLong(id);
						Optional<HouseEntity> existingHouseOptional = houseRepository.findById(idL);
						if (existingHouseOptional.isPresent()) {
							HouseEntity existingHouse = existingHouseOptional.get();
							existingHouse.setHouseName(updatedHouse.getHouseName());
							existingHouse.setWarnId(updatedHouse.getWarnId());
							existingHouse.setAddress(updatedHouse.getAddress());
							existingHouse = houseRepository.save(existingHouse);
							responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_UPDATE_SUCCESS), HttpStatus.OK);
						} else {
							responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_HOUSE_NOT_FOUND), HttpStatus.BAD_REQUEST);
						}
					} else {
						responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ID_FORMAT_INVALID), HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else{
					responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_HOUSE_EXISTS), HttpStatus.BAD_REQUEST);
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
					Optional<HouseEntity> house = this.houseRepository.findById(idL);
					if (house.isPresent()) {
						this.houseRepository.deleteById(idL);
						responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_DELETE_SUCCESS), HttpStatus.OK);
					} else {
						responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_HOUSE_NOT_FOUND), HttpStatus.BAD_REQUEST);
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
		public ResponseEntity<?> getStatusHouse() {
			ResponseEntity<?> responseEntity;
			try {
				List<HouseEntity> inforHouseEntityList = houseRepository.findAll();
				List<InforRoomStatusDto> statusDtoList = new ArrayList<>();
				for (HouseEntity house : inforHouseEntityList) {
					List<Integer> rooms = house.getRooms().stream()
							.filter(r -> r.getEmptyRoom() == 0)
							.map(InforRoomEntity::getNumberRoom)
							.toList();
					statusDtoList.add(new InforRoomStatusDto(house.getHouseName(),rooms));
				}
				responseEntity = new ResponseEntity<>(statusDtoList, HttpStatus.OK);
			} catch (Exception e){
				responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return responseEntity;
		}

		private void handleChangerRoomStatus() {
			List<InforRoomEntity> inforRoomEntityList = roomRepository.findAll();
			for (InforRoomEntity room : inforRoomEntityList) {
				int tenantNum = room.getTenantList().size();
				if(tenantNum > 0){
					room.setEmptyRoom(1);
				} else{
					room.setEmptyRoom(0);
				}
				roomRepository.save(room);
			}
		}
}
