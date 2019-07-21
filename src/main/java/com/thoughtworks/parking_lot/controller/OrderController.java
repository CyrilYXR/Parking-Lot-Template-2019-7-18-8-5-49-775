package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.entity.ParkingOrder;
import com.thoughtworks.parking_lot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity add(@RequestBody ParkingOrder parkingOrder){
        ParkingOrder order = orderService.add(parkingOrder);
        return ResponseEntity.ok().body(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ParkingOrder parkingOrder){
        ParkingOrder update = orderService.update(id, parkingOrder);
        return ResponseEntity.ok().body(update);
    }
}
