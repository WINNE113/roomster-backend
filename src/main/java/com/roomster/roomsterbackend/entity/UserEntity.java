package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "phone_number"))
public class UserEntity extends BaseEntity implements UserDetails {

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @Column(name = "images")
    private String images;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "phone_number_confirmed")
    private boolean phoneNumberConfirmed;

    @Column(name = "two_factor_enable")
    private boolean TwoFactorEnable;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<PostEntity> posts = new ArrayList<>();

    @OneToOne(mappedBy = "userRating")
    private RatingEntity ratingEntity;

    @OneToMany(mappedBy = "userChatMessage")
    private List<ChatMessageEntity> chatMessage = new ArrayList<>();


    public UserEntity(){}


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

    public boolean getPhoneNumberConfirmed(){return phoneNumberConfirmed;}

    public void setPhoneNumberConfirmed(boolean phoneNumberConfirmed){
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public boolean getTwoFactorEnable() {
        return TwoFactorEnable;
    }

    public void setTwoFactorEnable(boolean twoFactorEnable) {
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

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(RoleEntity role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
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
