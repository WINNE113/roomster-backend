package com.roomster.roomsterbackend.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.auth.ChangePasswordRequest;
import com.roomster.roomsterbackend.dto.user.UpdateProfileRequest;
import com.roomster.roomsterbackend.dto.user.UserDto;
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
    @PostMapping(value = "/update-profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse saveNewPost(@RequestPart String profileRequest,@RequestPart(required = false,name = "images") @Valid MultipartFile images, Principal connectedUser) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        UpdateProfileRequest profile = objectMapper.readValue(profileRequest, UpdateProfileRequest.class);

        return userService.updateProfile(profile, images, connectedUser);
    }
    @PatchMapping("/update-password")
    public BaseResponse changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Principal connectedUser){
       return userService.changePassword(changePasswordRequest, connectedUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete")
    public BaseResponse deleteUserById(@RequestParam Long userId){
        try{
            userService.deleteUserById(userId);
        }catch (Exception ex){
            return BaseResponse.error("Ex: " + ex.getMessage());
        }
        return BaseResponse.success("Xóa người dùng thành công");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-user")
    public UserDto getUserById(Long userId){
        return userService.getUserById(userId);
    }
}
