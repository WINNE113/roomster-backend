package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity {

    @Column(name = "author_id")
    private Long authorId;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private InforRoomEntity room;

    @ManyToOne()
    @JoinColumn(name = "post_type_id", referencedColumnName = "id")
    private PostTypeEntity postType;

}
