package com.roomster.roomsterbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "infor_rooms")
public class InforRoomEntity extends BaseEntity{

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

    @OneToOne(mappedBy = "room")
    private PostEntity post;

    public InforRoomEntity(){}

    public InforRoomEntity(int numberRoom, int emptyRoom, int stayMax, double acreage, BigDecimal price, BigDecimal electricityPrice, BigDecimal waterPrice, PostEntity post) {
        this.numberRoom = numberRoom;
        this.emptyRoom = emptyRoom;
        this.stayMax = stayMax;
        this.acreage = acreage;
        this.price = price;
        this.electricityPrice = electricityPrice;
        this.waterPrice = waterPrice;
        this.post = post;
    }

    public InforRoomEntity(Date createdDate, Date modifiedDate, String createdBy, String modifiedBy, int numberRoom, int emptyRoom, int stayMax, double acreage, BigDecimal price, BigDecimal electricityPrice, BigDecimal waterPrice, PostEntity post) {
        super(createdDate, modifiedDate, createdBy, modifiedBy);
        this.numberRoom = numberRoom;
        this.emptyRoom = emptyRoom;
        this.stayMax = stayMax;
        this.acreage = acreage;
        this.price = price;
        this.electricityPrice = electricityPrice;
        this.waterPrice = waterPrice;
        this.post = post;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public int getEmptyRoom() {
        return emptyRoom;
    }

    public void setEmptyRoom(int emptyRoom) {
        this.emptyRoom = emptyRoom;
    }

    public int getStayMax() {
        return stayMax;
    }

    public void setStayMax(int stayMax) {
        this.stayMax = stayMax;
    }

    public double getAcreage() {
        return acreage;
    }

    public void setAcreage(double acreage) {
        this.acreage = acreage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(BigDecimal electricityPrice) {
        this.electricityPrice = electricityPrice;
    }

    public BigDecimal getWaterPrice() {
        return waterPrice;
    }

    public void setWaterPrice(BigDecimal waterPrice) {
        this.waterPrice = waterPrice;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
