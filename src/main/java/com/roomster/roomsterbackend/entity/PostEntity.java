package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "description")
    private String description;

    @Column(name = "object")
    private String object;

    @Column(name = "convenient")
    private String convenient;

    @Column(name = "surroundings")
    private String surroundings;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_type_id", referencedColumnName = "id")
    private PostTypeEntity postType;

    @OneToMany(mappedBy = "postComment")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "postImage")
    private List<ImageEntity> image = new ArrayList<>();

    @OneToMany(mappedBy = "postRating")
    private List<RatingEntity> ratings = new ArrayList<>();

    public PostEntity(){}

    public PostEntity(String title, String address, String publisher, String description, String object, String convenient, String surroundings, UserEntity user, RoomEntity room, PostTypeEntity postType, List<Comment> comments, List<ImageEntity> image, List<RatingEntity> ratings) {
        this.title = title;
        this.address = address;
        this.publisher = publisher;
        this.description = description;
        this.object = object;
        this.convenient = convenient;
        this.surroundings = surroundings;
        this.user = user;
        this.room = room;
        this.postType = postType;
        this.comments = comments;
        this.image = image;
        this.ratings = ratings;
    }

    public PostEntity(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, String title, String address, String publisher, String description, String object, String convenient, String surroundings, UserEntity user, RoomEntity room, PostTypeEntity postType, List<Comment> comments, List<ImageEntity> image, List<RatingEntity> ratings) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.title = title;
        this.address = address;
        this.publisher = publisher;
        this.description = description;
        this.object = object;
        this.convenient = convenient;
        this.surroundings = surroundings;
        this.user = user;
        this.room = room;
        this.postType = postType;
        this.comments = comments;
        this.image = image;
        this.ratings = ratings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getConvenient() {
        return convenient;
    }

    public void setConvenient(String convenient) {
        this.convenient = convenient;
    }

    public String getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(String surroundings) {
        this.surroundings = surroundings;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public PostTypeEntity getPostType() {
        return postType;
    }

    public void setPostType(PostTypeEntity postType) {
        this.postType = postType;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<ImageEntity> getImage() {
        return image;
    }

    public void setImage(List<ImageEntity> image) {
        this.image = image;
    }

    public List<RatingEntity> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingEntity> ratings) {
        this.ratings = ratings;
    }
}
