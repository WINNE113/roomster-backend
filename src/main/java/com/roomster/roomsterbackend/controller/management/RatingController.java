package com.roomster.roomsterbackend.controller.management;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.rating.RatingDto;
import com.roomster.roomsterbackend.service.IService.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RatingController {

    private final IRatingService ratingService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/new")
    public BaseResponse saveNewRating(@RequestBody RatingDto ratingDto, Principal connectedUser) {
        try {
            ratingService.saveNewRating(ratingDto, connectedUser);
        }catch (Exception ex){
            BaseResponse.error(ex.getMessage());
        }
        return BaseResponse.success("Thêm rating thành công!");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update")
    public BaseResponse updateRating(@RequestParam Long ratingId, @RequestBody RatingDto ratingDto) {
        return ratingService.updateRating(ratingId, ratingDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public BaseResponse deleteCommentPost(@RequestParam Long ratingId) {
        try {
            ratingService.deleteRating(ratingId);
        }catch (Exception ex){
            return BaseResponse.error("Xóa Thất Bại!");
        }
        return BaseResponse.success("Xóa Thành Công!");
    }

    @GetMapping("/list/{postId}")
    public List<RatingDto> getAllRatingOfPost(@PathVariable(name = "postId") Long postId) {
        return ratingService.getAllRatingByPost(postId);
    }
}
