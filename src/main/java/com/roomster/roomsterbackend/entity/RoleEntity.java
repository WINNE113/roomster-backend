package com.roomster.roomsterbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();

    public RoleEntity(){}

    public RoleEntity(String name, String code, List<UserEntity> users) {
        this.name = name;
        this.code = code;
        this.users = users;
    }

    public RoleEntity(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, String name, String code, List<UserEntity> users) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.name = name;
        this.code = code;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
