package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.PostTypeDto;
import com.roomster.roomsterbackend.entity.PostTypeEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-26T21:22:15+0700",
    comments = "version: 1.4.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class PostTypeMapperImpl implements PostTypeMapper {

    @Override
    public PostTypeDto entityToDto(PostTypeEntity postTypeEntity) {
        if ( postTypeEntity == null ) {
            return null;
        }

        PostTypeDto postTypeDto = new PostTypeDto();

        postTypeDto.setName( postTypeEntity.getName() );
        postTypeDto.setCode( postTypeEntity.getCode() );
        postTypeDto.setDeleted( postTypeEntity.isDeleted() );
        postTypeDto.setCreatedDate( postTypeEntity.getCreatedDate() );
        postTypeDto.setModifiedDate( postTypeEntity.getModifiedDate() );
        postTypeDto.setCreatedBy( postTypeEntity.getCreatedBy() );
        postTypeDto.setModifiedBy( postTypeEntity.getModifiedBy() );

        return postTypeDto;
    }

    @Override
    public PostTypeEntity dtoToEntity(PostTypeDto postTypeDto) {
        if ( postTypeDto == null ) {
            return null;
        }

        PostTypeEntity postTypeEntity = new PostTypeEntity();

        postTypeEntity.setCreatedBy( postTypeDto.getCreatedBy() );
        postTypeEntity.setModifiedBy( postTypeDto.getModifiedBy() );
        postTypeEntity.setCreatedDate( postTypeDto.getCreatedDate() );
        postTypeEntity.setModifiedDate( postTypeDto.getModifiedDate() );
        postTypeEntity.setName( postTypeDto.getName() );
        postTypeEntity.setCode( postTypeDto.getCode() );
        postTypeEntity.setDeleted( postTypeDto.isDeleted() );

        return postTypeEntity;
    }
}
