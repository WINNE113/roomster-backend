package com.roomster.roomsterbackend.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class ClientController {
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("I'm Baba-Yaga");
    }
}
