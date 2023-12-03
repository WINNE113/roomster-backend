package com.roomster.roomsterbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    @Column(name = "city_name")
    private String cityName;

    @OneToMany(mappedBy = "city")
    @JsonManagedReference
    private List<District> districtList;

    // Constructors, getters, and setters
}
