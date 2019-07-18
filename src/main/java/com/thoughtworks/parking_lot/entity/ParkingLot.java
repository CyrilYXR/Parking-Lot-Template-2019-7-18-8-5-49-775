package com.thoughtworks.parking_lot.entity;

import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
public class ParkingLot {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

//    @Check(constraints = "capacity>=0")
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
