package com.roomster.roomsterbackend.entity;

import com.roomster.roomsterbackend.dto.RoleDTO;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "two_factor_enable")
    private int TwoFactorEnable;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts = new ArrayList<>();

    @OneToOne(mappedBy = "userRating")
    private RatingEntity ratingEntity;

    @Enumerated(EnumType.STRING)
    private RoleDTO role;


    public UserEntity(){}

    public UserEntity(String userName, String passwordHash, String email, String phoneNumber, int twoFactorEnable, boolean isActive, boolean isDeleted, Date dateOfBirth, String address, List<RoleEntity> roles, List<PostEntity> posts, RatingEntity ratingEntity, RoleDTO role) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        TwoFactorEnable = twoFactorEnable;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.roles = roles;
        this.posts = posts;
        this.ratingEntity = ratingEntity;
        this.role = role;
    }

    public UserEntity(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, String userName, String passwordHash, String email, String phoneNumber, int twoFactorEnable, boolean isActive, boolean isDeleted, Date dateOfBirth, String address, List<RoleEntity> roles, List<PostEntity> posts, RatingEntity ratingEntity, RoleDTO role) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.email = email;
        this.phoneNumber = phoneNumber;
        TwoFactorEnable = twoFactorEnable;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.roles = roles;
        this.posts = posts;
        this.ratingEntity = ratingEntity;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTwoFactorEnable() {
        return TwoFactorEnable;
    }

    public void setTwoFactorEnable(int twoFactorEnable) {
        TwoFactorEnable = twoFactorEnable;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }

    public RatingEntity getRatingEntity() {
        return ratingEntity;
    }

    public void setRatingEntity(RatingEntity ratingEntity) {
        this.ratingEntity = ratingEntity;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

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
