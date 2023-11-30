package com.roomster.roomsterbackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "city_id")
    private Long cityId;

    @ManyToOne()
    @JoinColumn(name = "city_id", updatable = false, insertable = false)
    @JsonBackReference
    private City city;

    @OneToMany(mappedBy = "district")
    @JsonManagedReference
    private List<Ward> wardList;

    // Constructors, getters, and setters
}
