package com.roomster.roomsterbackend.controller.admin;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.service.servicePackage.ServicePackageDto;
import com.roomster.roomsterbackend.service.IService.IPostService;
import com.roomster.roomsterbackend.service.IService.IServicePackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {
    private final IPostService postService;

    private final IServicePackageService servicePackageService;
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

    //TODO: CRUD service package


    @PostMapping(value = "/service/add-service-package")
    public ResponseEntity<?> addServicePackage(@RequestBody  ServicePackageDto request){
        return servicePackageService.addServicePackage(request);
    }

    @PutMapping(value = "/service/update-service-package/{id}")
    public ResponseEntity<?> updateServicePackage(@PathVariable Long id, @RequestBody ServicePackageDto request){
        return servicePackageService.updateServicePackage(id, request);
    }

    @DeleteMapping(value = "/service/delete-service-package/{id}")
    public ResponseEntity<?> deleteServicePackage(@PathVariable Long id){
        return servicePackageService.removeServicePackageById(id);
    }



}
