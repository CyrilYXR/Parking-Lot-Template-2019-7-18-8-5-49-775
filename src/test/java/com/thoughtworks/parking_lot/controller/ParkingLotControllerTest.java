package com.thoughtworks.parking_lot.controller;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import org.json.JSONObject;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_ok_when_add_a_parking_lot() throws Exception {

        ParkingLot parkingLot = new ParkingLot("parkingLot1",10,"position");
        //TODO id用自增类型有问题
        parkingLot.setId(1L);
        MvcResult mvcResult = this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot)))
                .andExpect(status().isCreated()).andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(parkingLot.getName(), jsonObject.getString("name"));
        Assertions.assertEquals(parkingLot.getCapacity().intValue(), jsonObject.getInt("capacity"));
    }
}
