package com.roomster.roomsterbackend.controller.management;

import com.roomster.roomsterbackend.dto.RatingDto;
import com.roomster.roomsterbackend.service.IService.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rating")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RatingController {
    private final IRatingService ratingService;

    @PostMapping("/new")
    public RatingDto saveNewRating(@RequestBody RatingDto ratingDto) {
        return ratingService.saveNewRating(ratingDto);
    }

    @PutMapping("/update")
    public RatingDto updateRating(@RequestBody RatingDto commentPostDTO) {
        return ratingService.updateRating(commentPostDTO);
    }

    @DeleteMapping("/delete")
    public void deleteCommentPost(@RequestBody RatingDto ratingDto) {
        ratingService.deleteRating(ratingDto);
    }

    @GetMapping("/list/{postId}")
    public List<RatingDto> getAllRatingOfPost(@PathVariable(name = "postId") Long postId) {
        return ratingService.getAllRatingByPost(postId);
    }
}
