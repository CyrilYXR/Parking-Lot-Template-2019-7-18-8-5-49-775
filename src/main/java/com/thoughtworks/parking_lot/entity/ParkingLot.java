package com.thoughtworks.parking_lot.entity;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ParkingLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Check(constraints = "capacity>=0")
    @Column
    private Integer capacity;

    @Column
    private String position;

    public ParkingLot() {
    }

    public ParkingLot(String name, Integer capacity, String position) {
        this.name = name;
        this.capacity = capacity;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
