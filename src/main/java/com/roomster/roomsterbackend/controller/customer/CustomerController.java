package com.roomster.roomsterbackend.controller.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @GetMapping
    public ResponseEntity<String> viewProfile(){
        return ResponseEntity.ok("I'm Baba-Yaga");
    }
}
