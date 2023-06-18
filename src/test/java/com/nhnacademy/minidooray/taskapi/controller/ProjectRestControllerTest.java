package com.nhnacademy.minidooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.minidooray.taskapi.domain.request.project.ProjectModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.project.ProjectRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.ProjectService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void getProjects() throws Exception{
        //given
        ProjectDto projectDto = ProjectDto.builder()
                .projectContent("content")
                .projectCreatedAt(LocalDateTime.now())
                .projectMemberId("test")
                .projectTitle("title")
                .projectMemberRole("ROLE_ADMIN")
                .statusCodeName("활성")
                .projectSeq(1)
                .build();

        List<ProjectDto> projects = new ArrayList<>();

        projects.add(projectDto);


        //when
        when(projectService.getProjects()).thenReturn(projects);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/projects")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(projectDto.getProjectContent()));
    }

    @Test
    @Order(2)
    void getProject() throws Exception{
        ProjectDto projectDto = ProjectDto.builder()
                .projectContent("content")
                .projectCreatedAt(LocalDateTime.now())
                .projectMemberId("test")
                .projectTitle("title")
                .projectMemberRole("ROLE_ADMIN")
                .statusCodeName("활성")
                .projectSeq(1)
                .build();

        when(projectService.getProject(anyInt())).thenReturn(projectDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}",1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(projectDto.getProjectTitle()));
    }


    @Test
    @Order(3)
    void createProject() throws Exception{
        ProjectRegisterRequest registerRequest = ProjectRegisterRequest.builder()
                .projectContent("content")
                .projectTitle("title")
                .build();

        ProjectDto projectDto = ProjectDto.builder()
                .projectContent("content")
                .projectCreatedAt(LocalDateTime.now())
                .projectMemberId("test")
                .projectTitle("title")
                .projectMemberRole("ROLE_ADMIN")
                .statusCodeName("활성")
                .projectSeq(1)
                .build();

        when(projectService.createProject(any(),any())).thenReturn(projectDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{accountId}","test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(registerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("ROLE_ADMIN"))
                .andExpect(jsonPath("$.member").value("test"));
    }

    @Test
    @Order(4)
    void createProjectNullTest() {
        ProjectRegisterRequest registerRequest = ProjectRegisterRequest.builder()
                .projectContent("content")
                .build();

        when(projectService.createProject(registerRequest, "test")).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> projectService.createProject(registerRequest, "test"));
    }

    @Test
    @Order(5)
    void modifyProject() throws Exception{
        ProjectModifyRequest modifyRequest = ProjectModifyRequest.builder()
                .projectContent("content-update")
                .projectTitle("title-update")
                .build();

        ProjectDto projectDto = ProjectDto.builder()
                .projectContent("content-update")
                .projectCreatedAt(LocalDateTime.now())
                .projectMemberId("test")
                .projectTitle("title-update")
                .projectMemberRole("ROLE_ADMIN")
                .statusCodeName("활성")
                .projectSeq(1)
                .build();

        when(projectService.modifyProject(any(), anyInt())).thenReturn(projectDto);


        mockMvc.perform(MockMvcRequestBuilders.patch("/projects/{projectId}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(modifyRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title-update"))
                .andExpect(jsonPath("$.content").value("content-update"));
    }

    @Test
    @Order(6)
    void modifyProjectNullTest() {
        ProjectModifyRequest modifyRequest = ProjectModifyRequest.builder()
                .projectTitle("title-update")
                .build();

        when(projectService.modifyProject(modifyRequest, 1)).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> projectService.modifyProject(modifyRequest, 1));

    }

    @Test
    @Order(7)
    void deleteProject() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/projects/{projectId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}