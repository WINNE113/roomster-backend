package com.roomster.roomsterbackend.controller.user;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.UserDto;
import com.roomster.roomsterbackend.service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGE','ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @GetMapping("/view-profile")
    public UserDto viewProfile(){
        return userService.viewProfile();
    }
    @PostMapping("/update-password")
    public BaseResponse updatePassword(String oldPassword, String newPassword, String confirmPassword){
        return userService.updatePassword(oldPassword, newPassword, confirmPassword);
    }
}
