package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.RatingDto;
import com.roomster.roomsterbackend.entity.RatingEntity;
import com.roomster.roomsterbackend.mapper.RatingMapper;
import com.roomster.roomsterbackend.repository.RatingRepository;
import com.roomster.roomsterbackend.service.IService.IRatingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService implements IRatingService {
    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RatingMapper ratingMapper;
    @Override
    public RatingDto saveNewRating(RatingDto ratingDto) {
        return ratingMapper.entityToDTO(ratingRepository.save(ratingMapper.dtoToEntity(ratingDto)));
    }

    @Override
    public RatingDto updateRating(RatingDto ratingDto) {
        RatingEntity oldRating = ratingRepository.findById(ratingDto.getRatingId()).orElseThrow(EntityNotFoundException::new);
        return ratingMapper.entityToDTO(ratingMapper.updateRating(oldRating, ratingMapper.dtoToEntity(ratingDto)));
    }

    @Override
    public List<RatingDto> getAllRatingByPost(Long postId) {
        return ratingRepository.getRatingEntitiesByPostId(postId)
                .stream()
                .map(likePostEntity -> ratingMapper.entityToDTO(likePostEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRating(RatingDto ratingDto) {
        ratingRepository.delete(
                ratingMapper.dtoToEntity(ratingDto)
        );
    }
}
