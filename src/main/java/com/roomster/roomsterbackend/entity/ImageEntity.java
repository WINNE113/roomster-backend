package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "images")
public class ImageEntity extends BaseEntity{

    @Column(name = "origin_link_image")
    private String originLinkImage;

    @Column(name = "local_link_image")
    private String localLinkImage;

    @Column(name = "is_deleted")
    private boolean isDeleted;

   @ManyToOne()
   @JoinColumn(name = "post_id", nullable = false)
   private PostEntity postImage;

   public ImageEntity(){}
    public ImageEntity(String originLinkImage, String localLinkImage, boolean isDeleted, PostEntity postImage) {
        this.originLinkImage = originLinkImage;
        this.localLinkImage = localLinkImage;
        this.isDeleted = isDeleted;
        this.postImage = postImage;
    }

    public ImageEntity(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, String originLinkImage, String localLinkImage, boolean isDeleted, PostEntity postImage) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.originLinkImage = originLinkImage;
        this.localLinkImage = localLinkImage;
        this.isDeleted = isDeleted;
        this.postImage = postImage;
    }

    public String getOriginLinkImage() {
        return originLinkImage;
    }

    public void setOriginLinkImage(String originLinkImage) {
        this.originLinkImage = originLinkImage;
    }

    public String getLocalLinkImage() {
        return localLinkImage;
    }

    public void setLocalLinkImage(String localLinkImage) {
        this.localLinkImage = localLinkImage;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public PostEntity getPostImage() {
        return postImage;
    }

    public void setPostImage(PostEntity postImage) {
        this.postImage = postImage;
    }
}
