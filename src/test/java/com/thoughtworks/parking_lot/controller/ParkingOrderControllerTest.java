package com.thoughtworks.parking_lot.controller;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.parking_lot.entity.ParkingOrder;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderRepository;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private ParkingOrder initOrder;
    private ParkingLot initParkingLot;

    @Before
    public void initH2(){
        initParkingLot = new ParkingLot("pl", 2, "position");
        parkingLotRepository.save(initParkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("pl", "123456", new Date(), null, 1);
        initOrder = orderRepository.save(parkingOrder);
    }

    @After
    public void clearH2(){
        orderRepository.deleteAll();
        parkingLotRepository.deleteAll();
    }

    @Test
    public void should_return_ok_when_add_an_order() throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder("pl", "123123", new Date(), null, 1);
        MvcResult mvcResult = this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingOrder)))
                .andExpect(status().isOk()).andReturn();
        JSONObject orderSaved = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(parkingOrder.getCarNumber(), orderSaved.getString("carNumber"));
        Assertions.assertEquals(parkingOrder.getStatus().intValue(), orderSaved.getInt("status"));
    }

    @Test
    public void should_return_failed_when_add_an_order_with_error_parking_lot_name() throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder("An Error Name", "123123", new Date(), null, 1);
        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingOrder)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Parking lot name is not exist!"));
    }

    @Test
    public void should_return_ok_and_update_order_when_fetch_a_car() throws Exception {
        this.initOrder.setStatus(2);

        MvcResult mvcResult = this.mockMvc.perform(put("/orders/"+this.initOrder.getId())
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(initOrder)))
                .andExpect(status().isOk()).andReturn();
        JSONObject orderSaved = new JSONObject(mvcResult.getResponse().getContentAsString());

        ParkingLot parkingLot = parkingLotRepository.findByName(initOrder.getParkingLotName()).get(0);

        Assertions.assertEquals(initOrder.getCarNumber(), orderSaved.getString("carNumber"));
        Assertions.assertEquals(initOrder.getStatus().intValue(), orderSaved.getInt("status"));

        Assertions.assertEquals(initParkingLot.getCapacity() + 1, parkingLot.getCapacity().intValue());
    }



}
