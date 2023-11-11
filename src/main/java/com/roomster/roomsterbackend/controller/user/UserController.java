package com.roomster.roomsterbackend.controller.user;


import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.dto.UserDto;
import com.roomster.roomsterbackend.service.IService.IPostService;
import com.roomster.roomsterbackend.service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGE','ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    private final IPostService service;

    @GetMapping("/view-profile")
    public UserDto viewProfile(){
        return userService.viewProfile();
    }
    @PostMapping("/update-password")
    public BaseResponse updatePassword(String oldPassword, String newPassword, String confirmPassword){
        return userService.updatePassword(oldPassword, newPassword, confirmPassword);
    }

    @GetMapping("/quan-li-tin/{authorId}")
    public List<PostDto> getPostByUserId(@PathVariable Long authorId, Pageable pageable){
        return service.getPostByAuthorId(pageable, authorId);
    }
}
