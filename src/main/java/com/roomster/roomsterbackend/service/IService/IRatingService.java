package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.CommentPostDto;
import com.roomster.roomsterbackend.dto.RatingDto;

import java.util.List;

public interface IRatingService {
    RatingDto saveNewRating(RatingDto ratingDto);

    RatingDto updateRating(RatingDto ratingDto);

    List<RatingDto> getAllRatingByPost(Long postId);

    void deleteRating(RatingDto ratingDto);
}
