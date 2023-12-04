package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.entity.House;
import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.entity.RoomService;
import com.roomster.roomsterbackend.repository.HouseRepository;
import com.roomster.roomsterbackend.repository.RoomRepository;
import com.roomster.roomsterbackend.service.IService.IRoomService;
import com.roomster.roomsterbackend.util.message.MessageUtil;
import com.roomster.roomsterbackend.util.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HouseRepository houseRepository;

    @Override
    public ResponseEntity<?> findAll() {
        ResponseEntity<?> responseEntity;
        try {
            List<InforRoomEntity> inforRoomEntityList = roomRepository.findAll();
            responseEntity = new ResponseEntity<>(inforRoomEntityList, HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<?> save(InforRoomEntity room) {
        ResponseEntity<?> responseEntity;
        try {
            // check house
            Long houseId = room.getHouseId();
            Optional<House> house = this.houseRepository.findById(houseId);
            if (house.isPresent()) {
                // validate room
                room = roomRepository.save(room);
                responseEntity = new ResponseEntity<>(room, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_HOUSE_NOT_FOUND), HttpStatus.METHOD_NOT_ALLOWED);
            }
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @Override
    public ResponseEntity<?> update(String id, InforRoomEntity newestRoom) {
        ResponseEntity<?> responseEntity;
        try {
            if (ValidatorUtils.isNumber(id)) {
                Long idL = Long.parseLong(id);
                Optional<InforRoomEntity> room = this.roomRepository.findById(idL);
                if (room.isPresent()) {
                    InforRoomEntity oldRoom = room.get();
                    oldRoom.setEmptyRoom(newestRoom.getEmptyRoom());
                    oldRoom.setNumberRoom(newestRoom.getNumberRoom());
                    oldRoom.setAcreage(newestRoom.getAcreage());
                    oldRoom.setPost(newestRoom.getPost());
                    oldRoom.setHouseId(newestRoom.getHouseId());
                    oldRoom.setPrice(newestRoom.getPrice());
                    oldRoom.setStayMax(newestRoom.getStayMax());
                    oldRoom.setWaterPrice(newestRoom.getWaterPrice());
                    oldRoom.setElectricityPrice(newestRoom.getElectricityPrice());
                    oldRoom = this.roomRepository.save(oldRoom);
                    responseEntity = new ResponseEntity<>(oldRoom, HttpStatus.OK);
                } else {
                    responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ROOM_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> findById(String id) {
        ResponseEntity<?> responseEntity;
        try {
            if (ValidatorUtils.isNumber(id)) {
                Long idL = Long.parseLong(id);
                Optional<InforRoomEntity> room = this.roomRepository.findById(idL);
                if (room.isPresent()) {
                    responseEntity = new ResponseEntity<>(room.get(), HttpStatus.OK);
                } else {
                    responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ROOM_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> delete(String id) {
        ResponseEntity<?> responseEntity;
        try {
            if (ValidatorUtils.isNumber(id)) {
                Long idL = Long.parseLong(id);
                Optional<InforRoomEntity> room = this.roomRepository.findById(idL);
                if (room.isPresent()) {
                    this.roomRepository.deleteById(idL);
                    responseEntity = new ResponseEntity<>(BaseResponse.success(MessageUtil.MSG_DELETE_SUCCESS), HttpStatus.OK);
                } else {
                    responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ROOM_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> findServicesByRoomId(String id) {
        ResponseEntity<?> responseEntity;
        try {
            if (ValidatorUtils.isNumber(id)) {
                Long roomId = Long.parseLong(id);
                Optional<InforRoomEntity> roomOptional = this.roomRepository.findById(roomId);
                
                if (roomOptional.isPresent()) {
                    InforRoomEntity room = roomOptional.get();
                    List<RoomService> services = room.getServices();
                    
                    responseEntity = new ResponseEntity<>(services, HttpStatus.OK);
                } else {
                    responseEntity = new ResponseEntity<>(BaseResponse.error(MessageUtil.MSG_ROOM_NOT_FOUND), HttpStatus.INTERNAL_SERVER_ERROR);
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