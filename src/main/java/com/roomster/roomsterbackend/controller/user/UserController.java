package com.roomster.roomsterbackend.controller.user;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.UpdateProfileRequest;
import com.roomster.roomsterbackend.dto.UserDto;
import com.roomster.roomsterbackend.service.IService.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGE','ROLE_ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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
