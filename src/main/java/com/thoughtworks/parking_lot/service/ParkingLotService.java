package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public void delete(Long id) {
        parkingLotRepository.deleteById(id);
    }

    public Page<ParkingLot> getByPage(int page, int pageSize){
        return parkingLotRepository.findAll(PageRequest.of(page-1, pageSize));
    }

    public ParkingLot getById(Long id) {
        if(parkingLotRepository.findById(id).isPresent()) {
            return parkingLotRepository.findById(id).get();
        }
        return null;
    }

    public ParkingLot update(Long id, ParkingLot parkingLot) {
        ParkingLot oldParkingLot = this.getById(id);
        oldParkingLot.setName(parkingLot.getName());
        oldParkingLot.setCapacity(parkingLot.getCapacity());
        oldParkingLot.setPosition(parkingLot.getPosition());
        return parkingLotRepository.save(oldParkingLot);
    }
}
