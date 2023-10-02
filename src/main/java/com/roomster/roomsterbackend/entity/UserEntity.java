package com.roomster.roomsterbackend.entity;

import com.roomster.roomsterbackend.dto.RoleDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder(builderMethodName = "userBuilder")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity implements UserDetails {



    @Column(name = "username")
    private String userName;

    @Column(name = "passwordhash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "twofactorenable")
    private int TwoFactorEnable;

    @Column(name = "isactive")
    private boolean isActive;

    @Column(name = "isdeleted")
    private boolean isDeleted;

    @Column(name = "dateofbirth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Enumerated(EnumType.STRING)
    private RoleDTO role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
