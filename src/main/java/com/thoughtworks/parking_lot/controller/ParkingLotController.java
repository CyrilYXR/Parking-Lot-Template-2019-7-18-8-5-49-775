package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping()
    public ResponseEntity add(@RequestBody ParkingLot parkingLot) {
        ParkingLot parkingLotSaved = parkingLotService.save(parkingLot);
//        ParkingLot parkingLotSaved = parkingLotRepository.save(parkingLot);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingLotSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        parkingLotService.delete(id);
        return ResponseEntity.ok().build();
    }

}
