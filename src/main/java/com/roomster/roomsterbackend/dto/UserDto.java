package com.roomster.roomsterbackend.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.roomster.roomsterbackend.entity.RoleEntity;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long userId;
    private String userName;

    private String password;

    private String email;

    private String phoneNumber;

    private boolean twoFactorEnable;

    private boolean isActive;

    private boolean isDeleted;

    private Date dateOfBirth;

    private String address;

    private Set<RoleEntity> roleList;
}
