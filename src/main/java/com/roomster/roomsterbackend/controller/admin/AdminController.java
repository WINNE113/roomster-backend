package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.dto.BaseResponse;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {
    private final IPostService postService;
    private final IUserService iUserService;

    @PatchMapping(value = "/setIsApprovedPost")
    public BaseResponse setIsApprovedPosts(Long[] listPostId){
        try {
            postService.setIsApprovedPosts(listPostId);
        }catch (Exception ex){
            return BaseResponse.error("Ex: " + ex.getMessage());
        }
        return BaseResponse.success("Bài viết cập nhật thành công");
    }

    @PatchMapping(value = "/setIsRejectedPost")
    public BaseResponse setIsRejectedPosts(Long[] listPostId){
        try {
            postService.setIsRejectedPosts(listPostId);
        }catch (Exception ex){
            return BaseResponse.error("Ex: " + ex.getMessage());
        }
        return BaseResponse.success("Bài viết cập nhật thành công");
    }
    @GetMapping("/list-user")
    public List<UserEntity> getAllUser() {
        return iUserService.getAllUser();
    }
}
