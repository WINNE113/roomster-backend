package com.roomster.roomsterbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostTypeDto {

    private Long id;

    private String name;

    private int code;

    private String is_delete;
}
