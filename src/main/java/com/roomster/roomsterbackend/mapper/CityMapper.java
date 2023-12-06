package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.common.CityDto;
import com.roomster.roomsterbackend.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {
    @Mapping(target = "cityId", source = "cityId")
    @Mapping(target = "cityName", source = "cityName")
    CityDto entityToDTO(City city);

    @Mapping(target = "cityId", source = "cityId")
    City dtoToEntity(CityDto cityDto);

}
