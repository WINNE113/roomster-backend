package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.common.DistrictDto;
import com.roomster.roomsterbackend.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DistrictMapper {
    @Mapping(target = "districtId", source = "districtId")
    @Mapping(target = "districtName", source = "districtName")
    DistrictDto entityToDTO(District district);

    @Mapping(target = "districtId", source = "districtId")
    District dtoToEntity(DistrictDto districtDto);
}
