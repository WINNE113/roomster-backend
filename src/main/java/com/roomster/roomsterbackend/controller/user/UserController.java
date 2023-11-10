package com.roomster.roomsterbackend.controller.user;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.ChangePasswordRequest;
import com.roomster.roomsterbackend.dto.UpdateProfileRequest;
import com.roomster.roomsterbackend.dto.UserDto;
import com.roomster.roomsterbackend.service.IService.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGE','ROLE_ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final IUserService userService;
    @GetMapping("/view-profile")
    public UserDto viewProfile(Principal connectedUser){
        return userService.viewProfile(connectedUser);
    }
    @PostMapping(value = "/update-profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public BaseResponse saveNewPost(@RequestBody UpdateProfileRequest profileRequest, @Valid MultipartFile images, Principal connectedUser) throws IOException {
        return userService.updateProfile(profileRequest, images, connectedUser);
    }
    @PatchMapping("/update-password")
    public BaseResponse changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Principal connectedUser){
       return userService.changePassword(changePasswordRequest, connectedUser);
    }

}
