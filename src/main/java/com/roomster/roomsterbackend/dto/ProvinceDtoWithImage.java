package com.roomster.roomsterbackend.dto;

import lombok.Data;

@Data
public class ProvinceDtoWithImage{
    private String provinceName;
    private Integer totalPosts;
    private String image;
}
