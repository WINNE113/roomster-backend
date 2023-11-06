package com.roomster.roomsterbackend.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    @GetMapping()
    public String get(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return "GET::Admin controller" + currentPrincipalName;
    }
    @PostMapping
    public String post(){
        return "POST::Admin controller";
    }
    @PutMapping
    public String put(){
        return "PUT::Admin controller";
    }
    @DeleteMapping
    public String delete(){
        return "DELETE::Admin controller";
    }
}
