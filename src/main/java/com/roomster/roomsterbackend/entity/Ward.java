package com.roomster.roomsterbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "wards")
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wardId;

    @Column(name = "ward_name")
    private String wardName;

    @Column(name = "district_id")
    private Long districtId;

    @ManyToOne()
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    @JsonBackReference
    private District district;


}