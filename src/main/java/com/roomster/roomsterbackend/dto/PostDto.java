package com.roomster.roomsterbackend.dto;

import com.roomster.roomsterbackend.entity.ImageEntity;
import com.roomster.roomsterbackend.entity.PostTypeEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto {

    private Long id;

    private String title;

    private boolean isActive;

    private String address;

    private String description;

    private String object;

    private String postType;

    private String imageUrlList;

    private List<ImageEntity> image;

    private double acreageRoom;

    private BigDecimal priceRoom;

    private String userName;

    private String phoneNumber;

    private PostTypeDto postTypeDto;
}
