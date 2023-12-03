package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.report.ReportDto;
import com.roomster.roomsterbackend.service.IService.IPostService;
import com.roomster.roomsterbackend.service.IService.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {
    private final IPostService postService;

    private final IReportService reportService;
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

    @GetMapping(value = "/report/getAllByPost")
    public List<ReportDto> getAllReportByPostId(@RequestParam Long postId){
        return reportService.getAllReportByPostId(postId);
    }

    @DeleteMapping("/report/delete")
    public BaseResponse deleteReportById(Long[] reportId){
        try {
            reportService.deleteReportById(reportId);
        }catch (Exception ex){
            return BaseResponse.error("Ex: " + ex.getMessage());
        }
        return BaseResponse.success("Xóa đánh giá thành công");
    }
}
