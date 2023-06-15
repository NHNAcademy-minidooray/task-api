package com.nhnacademy.minidooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.service.ProjectService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ProjectRestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    void getProjects() {
        //given

        //when

        //then

    }

    @Test
    void getProject() {
    }

    @Test
    void createProject() {
    }

    @Test
    void modifyProject() {
    }

    @Test
    void deleteProject() {
    }
}