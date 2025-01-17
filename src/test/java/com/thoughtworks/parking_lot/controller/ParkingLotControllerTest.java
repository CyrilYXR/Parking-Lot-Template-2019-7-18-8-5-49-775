package com.thoughtworks.parking_lot.controller;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.json.JSONArray;
import org.json.JSONObject;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Before
    public void initH2(){
        parkingLotRepository.deleteAll();
    }

    @Test
    public void should_return_ok_when_add_a_parking_lot() throws Exception {

        ParkingLot parkingLot = new ParkingLot("parkingLot1",10,"position");
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
        //when
        this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot)))
                .andExpect(status().isCreated()).andReturn();

        //then
        this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot1)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name has bean used!"));

    }

    @Test
    public void should_throw_exception_when_add_a_parking_lot_capacity_is_negative() throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot("parkingLot1",-1,"position");
        //when
        //then
//        Assertions.assertThrows(Exception.class, ()->{
//           this.mockMvc.perform(post("/parking-lots")
//                   .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot)));
//        });
        this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Capacity is negative!"));
    }

    @Test
    public void should_delete_a_parking_lot_when_delete_by_id() throws Exception {
        //given
        ParkingLot parkingLot = new ParkingLot("parkingLot1",2,"position");
        //when
        MvcResult mvcResult = this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot))).andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        //then
        this.mockMvc.perform(delete("/parking-lots/"+jsonObject.getLong("id")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_parking_lot_page_when_select_by_page() throws Exception {
        //given
        ParkingLot parkingLot1 = new ParkingLot("parkingLot1",2,"position");
        ParkingLot parkingLot2 = new ParkingLot("parkingLot2",2,"position");
        ParkingLot parkingLot3 = new ParkingLot("parkingLot3",2,"position");
        //when
        this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot1)));
        this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot2)));
        this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot3)));
        //then
        MvcResult mvcResult = this.mockMvc.perform(get("/parking-lots?page=1&pageSize=3"))
                .andExpect(status().isOk()).andReturn();
        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(3, jsonArray.length());
    }

    @Test
    public void should_return_parking_lot_when_find_by_id() throws Exception {
        //given
        ParkingLot parkingLot1 = new ParkingLot("parkingLot1",2,"position");
        ParkingLot parkingLot2 = new ParkingLot("parkingLot2",12,"position2");
        //when
        MvcResult mvcResultSaved = this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot1))).andReturn();
        JSONObject saved = new JSONObject(mvcResultSaved.getResponse().getContentAsString());

        this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot2)));
        //then
        MvcResult mvcResult = this.mockMvc.perform(get("/parking-lots/"+saved.getLong("id")))
                .andExpect(status().isOk()).andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(parkingLot1.getName(), jsonObject.getString("name"));
        Assertions.assertEquals(parkingLot1.getCapacity().intValue(), jsonObject.getInt("capacity"));
        Assertions.assertEquals(parkingLot1.getPosition(), jsonObject.getString("position"));
    }


    @Test
    public void should_update_parking_lot_when_update_by_id() throws Exception {
        //given
        ParkingLot parkingLot1 = new ParkingLot("parkingLot1",2,"position");
        MvcResult mvcResultSaved = this.mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot1))).andReturn();
        JSONObject saved = new JSONObject(mvcResultSaved.getResponse().getContentAsString());

        //when
        parkingLot1.setCapacity(10);
        MvcResult mvcResult = this.mockMvc.perform(put("/parking-lots/"+saved.getLong("id"))
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(parkingLot1))).andReturn();
        //then
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(10, jsonObject.getInt("capacity"));
    }



}
