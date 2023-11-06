package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ratings")
public class RatingEntity extends BaseEntity {

    @Column(name = "star_point")
    private double starPoint;

    @Column(name = "post_id")
    private Long postId;
    @Column(name = "user_id")
    private Long userId;

}
