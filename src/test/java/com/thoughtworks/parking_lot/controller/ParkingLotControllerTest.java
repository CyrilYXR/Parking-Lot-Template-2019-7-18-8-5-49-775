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

    @Test
    public void should_throw_exception_when_add_a_parking_lot_name_has_exist() throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot("parkingLot1",10,"position");
        ParkingLot parkingLot1 = new ParkingLot("parkingLot1",11,"position");
        parkingLot.setId(1L);
        parkingLot1.setId(2L);
        //when
        MvcResult mvcResult = this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot)))
                .andExpect(status().isCreated()).andReturn();
        //then
        Assertions.assertThrows(Exception.class, ()->{
           this.mockMvc.perform(post("/parking-lots")
                   .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot1)));
        });
    }

    @Test
    public void should_throw_exception_when_add_a_parking_lot_capacity_is_negative() {
        //given
        ParkingLot parkingLot = new ParkingLot("parkingLot1",-1,"position");
        parkingLot.setId(1L);
        //when
        //then
        Assertions.assertThrows(Exception.class, ()->{
           this.mockMvc.perform(post("/parking-lots")
                   .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot)));
        });
    }

    @Test
    public void should_delete_a_parking_lot_when_delete_by_id() throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot("parkingLot1",2,"position");
        parkingLot.setId(1L);
        //when
        this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot)));
        //then
        this.mockMvc.perform(delete("/parking-lots/1"))
                .andExpect(status().isOk());
    }


}
