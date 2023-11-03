package com.roomster.roomsterbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String userName;

    private String passwordHash;

    private String email;

    private String phoneNumber;

    private int twoFactorEnable;

    private boolean isActive;

    private boolean isDeleted;

    private Date dateOfBirth;

    private String address;
}
