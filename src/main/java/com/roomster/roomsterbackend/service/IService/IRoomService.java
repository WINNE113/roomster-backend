package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.entity.InforRoomEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRoomService {
    ResponseEntity<?> findAll();
    ResponseEntity<?> save(InforRoomEntity room);

    ResponseEntity<?> update(String id, InforRoomEntity newestRoom);

    ResponseEntity<?> findById(String id);

    ResponseEntity<?> delete(String id);
    
    ResponseEntity<?>  findServicesByRoomId(String id);
}
