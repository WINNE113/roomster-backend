package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ratings")
public class RatingEntity extends BaseEntity {

    @Column(name = "star_point")
    private String starPoint;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userRating;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postRating;

    public RatingEntity(){}

    public RatingEntity(String starPoint, UserEntity userRating, PostEntity postRating) {
        this.starPoint = starPoint;
        this.userRating = userRating;
        this.postRating = postRating;
    }

    public RatingEntity(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, String starPoint, UserEntity userRating, PostEntity postRating) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.starPoint = starPoint;
        this.userRating = userRating;
        this.postRating = postRating;
    }

    public String getStarPoint() {
        return starPoint;
    }

    public void setStarPoint(String starPoint) {
        this.starPoint = starPoint;
    }

    public UserEntity getUserRating() {
        return userRating;
    }

    public void setUserRating(UserEntity userRating) {
        this.userRating = userRating;
    }

    public PostEntity getPostRating() {
        return postRating;
    }

    public void setPostRating(PostEntity postRating) {
        this.postRating = postRating;
    }
}
