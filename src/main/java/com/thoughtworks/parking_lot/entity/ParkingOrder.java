package com.thoughtworks.parking_lot.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ParkingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "parkingLotName", referencedColumnName = "name")
    private String parkingLotName;

    private String carNumber;

    private Date createTime;

    private Date endTime;

    /**
     * 1: 开启  2： 关闭
     */
    private Integer status;

    public ParkingOrder() {
    }

    public ParkingOrder(String parkingLotName, String carNumber, Date createTime, Date endTime, Integer status) {
        this.parkingLotName = parkingLotName;
        this.carNumber = carNumber;
        this.createTime = createTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
