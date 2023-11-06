package com.roomster.roomsterbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "post_types")
public class PostTypeEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToOne(mappedBy = "postType")
    private PostEntity post;

    public PostTypeEntity(){}

    public PostTypeEntity(String name, String code, boolean isDeleted, PostEntity post) {
        this.name = name;
        this.code = code;
        this.isDeleted = isDeleted;
        this.post = post;
    }

    public PostTypeEntity(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, String name, String code, boolean isDeleted, PostEntity post) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.name = name;
        this.code = code;
        this.isDeleted = isDeleted;
        this.post = post;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
