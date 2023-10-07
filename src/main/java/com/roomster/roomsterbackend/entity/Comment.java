package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity postComment;

    public Comment(){}
    public Comment(String title, String content, boolean status, PostEntity postComment) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.postComment = postComment;
    }

    public Comment(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, String title, String content, boolean status, PostEntity postComment) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.title = title;
        this.content = content;
        this.status = status;
        this.postComment = postComment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PostEntity getPostComment() {
        return postComment;
    }

    public void setPostComment(PostEntity postComment) {
        this.postComment = postComment;
    }
}
