package com.roomster.roomsterbackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "houses")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseId;

    @Column(name = "house_name")
    private String houseName;

    @Column(name = "warn_id")
    private Long warnId;

    @ManyToOne()
    @JoinColumn(name = "warn_id", insertable = false, updatable = false)
    @JsonBackReference
    private Ward ward;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "house" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<InforRoomEntity> rooms;

    // Constructors, getters, and setters
}
