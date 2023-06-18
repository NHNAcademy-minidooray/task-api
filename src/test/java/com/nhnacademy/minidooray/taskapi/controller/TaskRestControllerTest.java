package com.nhnacademy.minidooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.minidooray.taskapi.domain.request.task.TaskModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.task.TaskRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.TaskService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskRestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TaskService taskService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @Order(1)
    void getProjectTasks() throws Exception {
        TaskListDto taskListDto1 = TaskListDto.builder()
                .taskSeq(1)
                .taskTitle("task-title-1")
                .projectSeq(1)
                .projectTitle("project-1")
                .build();

        TaskListDto taskListDto2 = TaskListDto.builder()
                .taskSeq(2)
                .taskTitle("task-title-2")
                .projectSeq(2)
                .projectTitle("project-2")
                .build();

        List<TaskListDto> tasks = List.of(taskListDto1, taskListDto2);

        when(taskService.getProjectTasks(any())).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(taskListDto1.getTaskTitle()))
                .andExpect(jsonPath("$[1].title").value(taskListDto2.getTaskTitle()));
    }

    @Test
    @Order(2)
    void getTask() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .taskSeq(1)
                .taskTitle("task-title-1")
                .taskCreatedAt(LocalDateTime.now())
                .taskContent("task-content-1")
                .milestoneName("milestone-1")
                .projectMemberId("member-1")
                .projectMemberRole("ROLE_MEMBER")
                .tagNames(List.of("tag1", "tag2"))
                .build();

        when(taskService.getTask(anyInt(), anyInt())).thenReturn(taskDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks/{taskId}", 1, 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tag[0]").value("tag1"))
                .andExpect(jsonPath("$.milestone").value(taskDto.getMilestoneName()))
                .andExpect(jsonPath("$.writer").value(taskDto.getProjectMemberId()));
    }

    @Test
    @Order(3)
    void getTasks() throws Exception {
        TaskListDto taskListDto1 = TaskListDto.builder()
                .taskSeq(1)
                .taskTitle("task-title-1")
                .projectSeq(1)
                .projectTitle("project-1")
                .build();

        TaskListDto taskListDto2 = TaskListDto.builder()
                .taskSeq(2)
                .taskTitle("task-title-2")
                .projectSeq(2)
                .projectTitle("project-2")
                .build();

        List<TaskListDto> tasks = List.of(taskListDto1, taskListDto2);

        when(taskService.getTasks(anyInt(), anyString())).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks/accounts/{accountId}", 1, "member-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    @Order(4)
    void getTaskAll() throws Exception {
        TaskListDto taskListDto1 = TaskListDto.builder()
                .taskSeq(1)
                .taskTitle("task-title-1")
                .projectSeq(1)
                .projectTitle("project-1")
                .build();

        TaskListDto taskListDto2 = TaskListDto.builder()
                .taskSeq(2)
                .taskTitle("task-title-2")
                .projectSeq(2)
                .projectTitle("project-2")
                .build();

        List<TaskListDto> tasks = List.of(taskListDto1, taskListDto2);

        when(taskService.getTaskAll(any())).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/tasks/{accountId}", "member-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(taskListDto1.getTaskTitle()))
                .andExpect(jsonPath("$[1].title").value(taskListDto2.getTaskTitle()));

    }

    @Test
    @Order(5)
    void createTask() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .taskSeq(1)
                .taskTitle("task-title-1")
                .taskCreatedAt(LocalDateTime.now())
                .taskContent("task-content-1")
                .milestoneName("milestone-1")
                .projectMemberId("member-1")
                .projectMemberRole("ROLE_MEMBER")
                .tagNames(List.of("tag1", "tag2"))
                .build();

        TaskRegisterRequest request = TaskRegisterRequest.builder()
                .content("task-content-1")
                .milestoneName("milestone-1")
                .tagNames(List.of("tag1", "tag2"))
                .title("task-title-1")
                .build();

        when(taskService.createTask(any(), any(), any())).thenReturn(taskDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/tasks/accounts/{accountId}", 1, "member-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.writer").value("member-1"));

    }

    @Test
    @Order(6)
    void createTaskNullTest() {
        TaskRegisterRequest request = TaskRegisterRequest.builder()
                .content(null)
                .title("task-title-1")
                .build();

        when(taskService.createTask(request, 1, "member-1")).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> taskService.createTask(request, 1, "member-1"));
    }

    @Test
    @Order(7)
    void modifyTask() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .taskSeq(1)
                .taskTitle("task-title-1")
                .taskContent("task-content-1")
                .taskCreatedAt(LocalDateTime.now())
                .milestoneName(null)
                .tagNames(List.of("tag3"))
                .projectMemberId("member-1")
                .projectMemberRole("ROLE_MEMBER")
                .build();

        TaskModifyRequest request = TaskModifyRequest.builder()
                .title("task-title-1")
                .content("task-content-1")
                .milestoneName(null)
                .tagNames(List.of("tag3"))
                .build();

        when(taskService.modifyTask(any(), any(), any(), any())).thenReturn(taskDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/projects/{projectId}/tasks/{taskId}/accounts/{accountId}", 1, 1, "member-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.writer").value("member-1"))
                .andExpect(jsonPath("$.tag[0]").value("tag3"))
                .andExpect(jsonPath("$.milestone").doesNotExist());
    }

    @Test
    @Order(8)
    void modifyTaskNullTest() {
        TaskModifyRequest request = TaskModifyRequest.builder()
                .title("task-title-1")
                .content(null)
                .milestoneName(null)
                .tagNames(List.of("tag3"))
                .build();

        when(taskService.modifyTask(request, 1, 1, "member-1")).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> taskService.modifyTask(request, 1, 1, "member-1"));
    }


    @Test
    @Order(9)
    void deleteTask() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/projects/{projectId}/tasks/{taskId}/accounts/{accountId}", 1, 1, "member-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}