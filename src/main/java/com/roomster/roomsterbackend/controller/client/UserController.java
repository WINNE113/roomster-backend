package com.roomster.roomsterbackend.controller.client;

import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.service.IService.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vi/auth/user")
@RequiredArgsConstructor
public class UserController {

    private final IPostService service;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/quan-ly-tin")
    public List<PostEntity> getAllPost(){
        return service.getAllPost();
    }


}
