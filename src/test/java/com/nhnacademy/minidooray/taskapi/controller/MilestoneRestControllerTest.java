package com.nhnacademy.minidooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.minidooray.taskapi.domain.request.milestone.MilestoneModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.milestone.MilestoneRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.MilestoneService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Order(1)
    void getMilestones() throws Exception {
        MilestoneDto milestoneDto = MilestoneDto.builder()
                .name("milestone")
                .id(1)
                .start(null)
                .end(null)
                .build();

        List<MilestoneDto> milestones = new ArrayList<>();
        milestones.add(milestoneDto);

        when(milestoneService.getMilestones(any())).thenReturn(milestones);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/milestones", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("milestone"));
    }

    @Test
    @Order(2)
    void getTasks() throws Exception {
        TaskListDto taskListDto= TaskListDto.builder()
                .taskSeq(1)
                .taskTitle("task-title-1")
                .projectSeq(1)
                .projectTitle("project-1")
                .build();


        List<TaskListDto> tasks = new ArrayList<>();
        tasks.add(taskListDto);

        when(milestoneService.getTasks(any(), any())).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks/milestones/{milestoneId}", 1, 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(taskListDto.getTaskTitle()));
    }

    @Test
    @Order(3)
    void getMilestone() throws Exception {
        MilestoneDto milestoneDto = MilestoneDto.builder()
                .name("milestone")
                .id(1)
                .start(null)
                .end(null)
                .build();

        when(milestoneService.getMilestone(any(), any())).thenReturn(milestoneDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/milestones/{milestoneId}", 1, 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(milestoneDto.getId()));
    }

    @Test
    @Order(4)
    void createMilestone() throws Exception{
        MilestoneDto milestoneDto = MilestoneDto.builder()
                .name("milestone")
                .id(1)
                .start(LocalDate.now())
                .end(null)
                .build();

        MilestoneRegisterRequest request = MilestoneRegisterRequest.builder()
                .name("milestone")
                .start(LocalDate.now())
                .end(null)
                .build();

        when(milestoneService.createMilestone(any(), any())).thenReturn(milestoneDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/milestones", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value(request.getName()));
    }

    @Test
    @Order(5)
    void createMilestoneNullTest() {
        MilestoneRegisterRequest request = MilestoneRegisterRequest.builder()
                .name(null)
                .build();
        when(milestoneService.createMilestone(request, 1)).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> milestoneService.createMilestone(request, 1));
    }

    @Test
    @Order(6)
    void modifyMilestone() throws Exception{
        MilestoneDto milestoneDto = MilestoneDto.builder()
                .name("milestone-update")
                .id(1)
                .start(LocalDate.now())
                .end(null)
                .build();

        MilestoneModifyRequest request = MilestoneModifyRequest.builder()
                .name("milestone-update")
                .start(LocalDate.now())
                .end(null)
                .build();

        when(milestoneService.modifyMilestone(any(), any(), any())).thenReturn(milestoneDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/projects/{projectId}/milestones/{milestoneId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(milestoneDto.getName()));

    }

    @Test
    @Order(7)
    void modifyMilestoneNullTest() throws Exception{
        MilestoneModifyRequest request = MilestoneModifyRequest.builder()
                .name("")
                .start(LocalDate.now())
                .end(null)
                .build();

        when(milestoneService.modifyMilestone(request, 1, 1)).thenThrow(ValidationFailedException.class);
        Assertions.assertThrows(ValidationFailedException.class,
                () -> milestoneService.modifyMilestone(request,1, 1));

    }

    @Test
    @Order(8)
    void deleteMilestone() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/projects/{projectId}/milestones/{milestoneId}", 1, 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}