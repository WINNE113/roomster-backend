package com.roomster.roomsterbackend.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.roomster.roomsterbackend.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long userId;

    private String userName;

    private String password;

    private String email;

    private String images;

    private String phoneNumber;

    private boolean phoneNumberConfirmed;

    private boolean twoFactorEnable;

    private boolean isActive;

    private boolean isDeleted;

    private Date dateOfBirth;

    private String address;

    private Set<RoleEntity> roleList;
}
