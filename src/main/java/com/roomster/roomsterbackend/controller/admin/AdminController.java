package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.service.IService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private IUserService service;
    @GetMapping()
    public String get(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return "GET::Admin controller" + currentPrincipalName;
    }

    @GetMapping("/list-user")
    public List<UserEntity> getAllUser() {
        return service.getAllUser();
    }
}
