package com.roomster.roomsterbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roomster.roomsterbackend.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoWithRating {
    private Long id;
    private String title;
    private String address;
    private Long createdBy;
    private Date createdDate;
    private BigDecimal price;
    private boolean isDeleted;
    private Double averageRating;
}
