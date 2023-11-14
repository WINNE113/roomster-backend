package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.dto.PostTypeDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.entity.PostTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostTypeMapper {
    PostTypeDto entityToDto(PostTypeEntity postTypeEntity);
    PostTypeEntity dtoToEntity(PostTypeDto postTypeDto);
}
