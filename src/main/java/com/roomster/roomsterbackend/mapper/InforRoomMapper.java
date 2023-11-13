package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.InforRoomDto;
import com.roomster.roomsterbackend.entity.InforRoomEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InforRoomMapper {

    InforRoomDto entityToDto(InforRoomEntity inforRoomEntity);

    InforRoomEntity dtoToEntity(InforRoomDto postDTO);

}
