package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping()
    public ResponseEntity add(@RequestBody ParkingLot parkingLot) {
        ParkingLot parkingLotSaved = parkingLotService.save(parkingLot);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingLotSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        parkingLotService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = {"page", "pageSize"})
    public ResponseEntity getByPage(@RequestParam int page, @RequestParam int pageSize){
        Page<ParkingLot> parkingLotPage = parkingLotService.getByPage(page, pageSize);
        return ResponseEntity.ok().body(parkingLotPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        ParkingLot parkingLot = parkingLotService.getById(id);
        return ResponseEntity.ok().body(parkingLot);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody ParkingLot parkingLot){
        ParkingLot update = parkingLotService.update(id, parkingLot);
        return ResponseEntity.ok().body(update);
    }

}
