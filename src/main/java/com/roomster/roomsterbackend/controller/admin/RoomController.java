package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.entity.InforRoomEntity;
import com.roomster.roomsterbackend.service.IService.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/room-master/room")
public class RoomController {
    @Autowired
    IRoomService roomService;

    @GetMapping()
    public ResponseEntity<?> getAllRooms(){
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable String id){
        return roomService.findById(id);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getRoomStatus(){
        return roomService.getStatusRoom();
    }

    @GetMapping("/statusPayment")
    public ResponseEntity<?> getRoomStatusPayment(){
        return roomService.getStatusPayment();
    }

    @PostMapping()
    public ResponseEntity<?> addRoom(@RequestBody InforRoomEntity room){
        return roomService.save(room);
    }

    @PutMapping(value = "/{id}" , consumes = "application/json;charset=UTF-8")
    public ResponseEntity<?> updateRoom(@PathVariable String id, @RequestBody InforRoomEntity room){
        return roomService.update(id, room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable String id){
        return roomService.delete(id);
    }

    @GetMapping("/service-room/{id}")
    public ResponseEntity<?> getServiceInRoom(@PathVariable String id){
        return roomService.findServicesByRoomId(id);
    }
}
