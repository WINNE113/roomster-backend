package com.roomster.roomsterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roomster.roomsterbackend.common.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "object")
    private String object;

    @Column(name = "convenient")
    private String convenient;

    @Column(name = "surroundings")
    private String surroundings;

    @Column(name = "image_url_list")
    @ElementCollection
    private List<String> imageUrlList;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "rotation")
    private String rotation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private InforRoomEntity roomId;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "post_type_id", referencedColumnName = "id")
    private PostTypeEntity postType;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity authorId;

    @OneToMany(mappedBy = "postId")
    private List<ReportEntity> reports;

    public UserEntity getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UserEntity authorId) {
        this.authorId = authorId;
    }
}
