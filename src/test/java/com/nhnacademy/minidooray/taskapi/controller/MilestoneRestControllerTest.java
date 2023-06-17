package com.nhnacademy.minidooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.response.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.service.MilestoneService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MilestoneRestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MilestoneRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    private MilestoneService milestoneService;
    
    ObjectMapper objectMapper = new ObjectMapper();
    

    @Test
    void getMilestones() throws Exception{
        MilestoneDto milestoneDto = MilestoneDto.builder()
                .name("milestone")
                .id(1)
                .start(null)
                .end(null)
                .build();

        List<MilestoneDto> milestones = new ArrayList<>();
        milestones.add(milestoneDto);

        when(milestoneService.getMilestones(any())).thenReturn(milestones);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/milestones",1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("milestone"));
    }

    @Test
    void getTasks() {
    }

    @Test
    void getMilestone() {
    }

    @Test
    void createMilestone() {
    }

    @Test
    void modifyMilestone() {
    }

    @Test
    void deleteMilestone() {
    }
}