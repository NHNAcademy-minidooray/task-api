package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.service.TaskTagService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskTagRestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskTagRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskTagService taskTagService;

    @Test
    @Order(1)
    void getTagsByTaskSeq() throws Exception{
        TaskTagDto taskTagDto1 = TaskTagDto.builder()
                .taskSeq(1)
                .tagSeq(1)
                .build();

        TaskTagDto taskTagDto2 = TaskTagDto.builder()
                .taskSeq(1)
                .tagSeq(2)
                .build();

        List<TaskTagDto> taskTags = List.of(taskTagDto1, taskTagDto2);

        when(taskTagService.getTaskTagByTaskSeq(any())).thenReturn(taskTags);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasktags/{taskId}",1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].task-id").value(1))
                .andExpect(jsonPath("$[1].task-id").value(1));
    }
}