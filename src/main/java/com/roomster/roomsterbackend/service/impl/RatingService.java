package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.rating.RatingDto;
import com.roomster.roomsterbackend.entity.RatingEntity;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.mapper.RatingMapper;
import com.roomster.roomsterbackend.repository.RatingRepository;
import com.roomster.roomsterbackend.service.IService.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService implements IRatingService {
    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RatingMapper ratingMapper;
    @Override
    public RatingDto saveNewRating(RatingDto ratingDto, Principal connectedUser) {
        var user = (UserEntity)((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();
        ratingDto.setUserId(user.getId());
        return ratingMapper.entityToDTO(ratingRepository.save(ratingMapper.dtoToEntity(ratingDto)));
    }

    @Override
    public BaseResponse updateRating(Long ratingId, RatingDto ratingDto) {
        Optional<RatingEntity> oldRating = ratingRepository.findById(ratingId);
        if(oldRating.isPresent()){
            oldRating.get().setStarPoint(ratingDto.getStarPoint());
            ratingRepository.save(oldRating.get());
            return BaseResponse.success("Cập Nhật Thành Công!");
        }
        return BaseResponse.error("Cập Nhật Thất Bại!");
    }

    @Override
    public List<RatingDto> getAllRatingByPost(Long postId) {
        return ratingRepository.getRatingEntitiesByPostId(postId)
                .stream()
                .map(likePostEntity -> ratingMapper.entityToDTO(likePostEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}
