package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
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

    @OneToOne(mappedBy = "roomId")
    private PostEntity post;
}
