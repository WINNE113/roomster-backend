package com.roomster.roomsterbackend.dto;


import com.roomster.roomsterbackend.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long userId;

    private String userName;

    private String passwordHash;

    private String email;

    private String phoneNumber;

    private String images;

    private boolean phoneNumberConfirmed;

    private boolean twoFactorEnable;

    private boolean isActive;

    private boolean isDeleted;

    private Date dateOfBirth;

    private String address;

}
