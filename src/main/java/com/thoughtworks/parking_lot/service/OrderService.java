package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.entity.ParkingOrder;
import com.thoughtworks.parking_lot.exception.GlobalException;
import com.thoughtworks.parking_lot.repository.OrderRepository;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingOrder add(ParkingOrder parkingOrder) {
        List<ParkingLot> parkingLots = parkingLotRepository.findByName(parkingOrder.getParkingLotName());
        if(parkingLots.size() < 1){
            throw new GlobalException(3, "parking lot name is not exist!");
        }
        ParkingLot parkingLot = parkingLots.get(0);
        parkingLot.setCapacity(parkingLot.getCapacity() - 1);
        parkingLotRepository.saveAndFlush(parkingLot);
        return orderRepository.save(parkingOrder);
    }
}
