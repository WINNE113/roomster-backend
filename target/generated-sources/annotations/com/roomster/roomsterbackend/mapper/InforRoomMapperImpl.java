package com.roomster.roomsterbackend.mapper;

import com.roomster.roomsterbackend.dto.inforRoom.InforRoomDto;
import com.roomster.roomsterbackend.entity.InforRoomEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-26T21:22:15+0700",
    comments = "version: 1.4.0.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class InforRoomMapperImpl implements InforRoomMapper {

    @Override
    public InforRoomDto entityToDto(InforRoomEntity inforRoomEntity) {
        if ( inforRoomEntity == null ) {
            return null;
        }

        InforRoomDto inforRoomDto = new InforRoomDto();

        inforRoomDto.setNumberRoom( inforRoomEntity.getNumberRoom() );
        inforRoomDto.setEmptyRoom( inforRoomEntity.getEmptyRoom() );
        inforRoomDto.setStayMax( inforRoomEntity.getStayMax() );
        inforRoomDto.setAcreage( inforRoomEntity.getAcreage() );
        inforRoomDto.setPrice( inforRoomEntity.getPrice() );
        inforRoomDto.setElectricityPrice( inforRoomEntity.getElectricityPrice() );
        inforRoomDto.setWaterPrice( inforRoomEntity.getWaterPrice() );

        return inforRoomDto;
    }

    @Override
    public InforRoomEntity dtoToEntity(InforRoomDto postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        InforRoomEntity inforRoomEntity = new InforRoomEntity();

        inforRoomEntity.setNumberRoom( postDTO.getNumberRoom() );
        inforRoomEntity.setEmptyRoom( postDTO.getEmptyRoom() );
        inforRoomEntity.setStayMax( postDTO.getStayMax() );
        inforRoomEntity.setAcreage( postDTO.getAcreage() );
        inforRoomEntity.setPrice( postDTO.getPrice() );
        inforRoomEntity.setElectricityPrice( postDTO.getElectricityPrice() );
        inforRoomEntity.setWaterPrice( postDTO.getWaterPrice() );

        return inforRoomEntity;
    }
}
