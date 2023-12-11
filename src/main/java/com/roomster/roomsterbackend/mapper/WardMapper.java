package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.common.WardDto;
import com.roomster.roomsterbackend.entity.Ward;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface WardMapper {
    @Mapping(target = "wardId", source = "wardId")
    @Mapping(target = "wardName", source = "wardName")
    WardDto entityToDTO(Ward ward);

    @Mapping(target = "wardId", source = "wardId")
    Ward dtoToEntity(WardDto wardDto);
}
