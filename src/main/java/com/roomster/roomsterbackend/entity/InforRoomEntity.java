package com.roomster.roomsterbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "infor_rooms")
public class InforRoomEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_room")
    private int numberRoom;

    @Column(name = "empty_room")
    private int emptyRoom;

    @Column(name = "staymax")
    private int stayMax;

    @Column(name = "acreage")
    private double acreage;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "electricity_price")
    private BigDecimal electricityPrice;

    @Column(name = "water_price")
    private BigDecimal waterPrice;

    @Column(name = "id_house" )
    private Long houseId;

    @JsonManagedReference
    @OneToOne(mappedBy = "roomId")
    private PostEntity post;

    @ManyToOne()
    @JoinColumn(name = "id_house" , insertable=false, updatable=false)
    @JsonBackReference
    private House house;

    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "inforRoomEntity")
    private List<RoomService> services;

    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL)
    private List<Order> orders;

    @JsonManagedReference
    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL)
    private List<Tenant> tenantList;
}
