package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.service.IService.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {
    private final IPostService postService;
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
}
