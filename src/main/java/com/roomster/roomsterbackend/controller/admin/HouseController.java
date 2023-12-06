package com.roomster.roomsterbackend.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.roomster.roomsterbackend.entity.House;
import com.roomster.roomsterbackend.service.IService.IHouseService;

@RestController
@PreAuthorize("hasRole('ROLE_MANAGE')")
@CrossOrigin("*")
@RequestMapping("/room-master/house")
public class HouseController {

    @Autowired
    private IHouseService houseService;

    @GetMapping()
    public ResponseEntity<?> getAllHouses() {
        return houseService.getAllHouses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHouseById(@PathVariable String id) {
        return houseService.getHouseById(id);
    }

    @PostMapping
    public ResponseEntity<?> createHouse(@RequestBody House house) {
        return houseService.createHouse(house);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHouse(@PathVariable String id, @RequestBody House house) {
        return houseService.updateHouse(id, house);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable String id) {
        return houseService.deleteHouse(id);
    }
}
