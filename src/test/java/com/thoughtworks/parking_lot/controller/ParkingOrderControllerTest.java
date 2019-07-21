package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.entity.ParkingOrder;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderRepository;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Before
    public void initH2(){
        ParkingLot parkingLot = new ParkingLot("pl", 10, "position");
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("pl", "123456", new Date(), null, 1);
        orderRepository.save(parkingOrder);
    }

    @After
    public void clearH2(){
        orderRepository.deleteAll();
        parkingLotRepository.deleteAll();
    }

    @Test
    public void should_return_ok_when_add_an_order() throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder("pl", "123123", new Date(), null, 1);
        orderRepository.save(parkingOrder);
    }
}
