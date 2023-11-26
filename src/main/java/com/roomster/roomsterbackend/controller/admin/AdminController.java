package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.service.IService.IPostService;
import com.roomster.roomsterbackend.service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final IPostService iPostService;

    private final IUserService iUserService;
    @GetMapping()
    public String get(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return "GET::Admin controller" + currentPrincipalName;
    }

    @GetMapping("/list-user")
    public List<UserEntity> getAllUser() {
        return iUserService.getAllUser();
    }

//    @GetMapping("/list-post")
//    public List<PostDto> getPostByType(){
//        return  iPostService.getAllPost();
//    }
}
