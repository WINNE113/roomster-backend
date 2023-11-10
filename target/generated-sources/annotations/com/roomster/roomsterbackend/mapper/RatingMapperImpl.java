package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.RatingDto;
import com.roomster.roomsterbackend.entity.RatingEntity;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-10T15:18:18+0700",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
*/
@Component
public class RatingMapperImpl implements RatingMapper {

    @Override
    public RatingDto entityToDTO(RatingEntity ratingEntity) {
        if ( ratingEntity == null ) {
            return null;
        }

        RatingDto ratingDto = new RatingDto();

        ratingDto.setRatingId( ratingEntity.getId() );

        return ratingDto;
    }

    @Override
    public RatingEntity dtoToEntity(RatingDto likePostDTO) {
        if ( likePostDTO == null ) {
            return null;
        }

        RatingEntity ratingEntity = new RatingEntity();

        ratingEntity.setId( likePostDTO.getRatingId() );

        return ratingEntity;
    }

    @Override
    public RatingEntity updateRating(RatingEntity oldRating, RatingEntity newRating) {
        if ( newRating == null ) {
            return null;
        }

        oldRating.setId( newRating.getId() );
        oldRating.setCreatedDate( newRating.getCreatedDate() );
        oldRating.setModifiedDate( newRating.getModifiedDate() );
        oldRating.setCreatedBy( newRating.getCreatedBy() );
        oldRating.setModifiedBy( newRating.getModifiedBy() );

        return oldRating;
    }
}
