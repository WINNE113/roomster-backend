package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.rating.RatingDto;
import com.roomster.roomsterbackend.entity.RatingEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-23T22:55:08+0700",
    comments = "version: 1.4.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class RatingMapperImpl implements RatingMapper {

    @Override
    public RatingDto entityToDTO(RatingEntity ratingEntity) {
        if ( ratingEntity == null ) {
            return null;
        }

        RatingDto ratingDto = new RatingDto();

        ratingDto.setRatingId( ratingEntity.getId() );
        ratingDto.setPostId( ratingEntity.getPostId() );
        ratingDto.setUserId( ratingEntity.getUserId() );
        ratingDto.setStarPoint( ratingEntity.getStarPoint() );

        return ratingDto;
    }

    @Override
    public RatingEntity dtoToEntity(RatingDto likePostDTO) {
        if ( likePostDTO == null ) {
            return null;
        }

        RatingEntity ratingEntity = new RatingEntity();

        ratingEntity.setId( likePostDTO.getRatingId() );
        ratingEntity.setStarPoint( likePostDTO.getStarPoint() );
        ratingEntity.setPostId( likePostDTO.getPostId() );
        ratingEntity.setUserId( likePostDTO.getUserId() );

        return ratingEntity;
    }

    @Override
    public RatingEntity updateRating(RatingEntity oldRating, RatingEntity newRating) {
        if ( newRating == null ) {
            return null;
        }

        oldRating.setCreatedBy( newRating.getCreatedBy() );
        oldRating.setModifiedBy( newRating.getModifiedBy() );
        oldRating.setId( newRating.getId() );
        oldRating.setCreatedDate( newRating.getCreatedDate() );
        oldRating.setModifiedDate( newRating.getModifiedDate() );
        oldRating.setStarPoint( newRating.getStarPoint() );
        oldRating.setPostId( newRating.getPostId() );
        oldRating.setUserId( newRating.getUserId() );

        return oldRating;
    }
}
