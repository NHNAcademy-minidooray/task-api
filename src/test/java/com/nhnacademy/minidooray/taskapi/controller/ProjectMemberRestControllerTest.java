package com.nhnacademy.minidooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.POJONode;
import com.nhnacademy.minidooray.taskapi.domain.request.projectmember.Member;
import com.nhnacademy.minidooray.taskapi.domain.request.projectmember.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.MemberListDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.ProjectMemberService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectMemberRestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectMemberRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectMemberService projectMemberService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    void getProjectMembers() throws Exception{
        ProjectMemberDto projectMemberDto1 = ProjectMemberDto.builder()
                .projectMemberSeq(1)
                .projectMemberId("admin")
                .projectMemberRole("ROLE_ADMIN")
                .build();

        ProjectMemberDto projectMemberDto2 = ProjectMemberDto.builder()
                .projectMemberSeq(2)
                .projectMemberId("member")
                .projectMemberRole("ROLE_MEMBER")
                .build();

        List<ProjectMemberDto> projectMembers = List.of(projectMemberDto1, projectMemberDto2);

        when(projectMemberService.getProjectMembers(any())).thenReturn(projectMembers);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/accounts",1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("admin"))
                .andExpect(jsonPath("$[1].username").value("member"));
    }

    @Test
    @Order(2)
    void getProjectMember() throws Exception{
        ProjectMemberDto projectMemberDto = ProjectMemberDto.builder()
                .projectMemberSeq(1)
                .projectMemberId("admin")
                .projectMemberRole("ROLE_ADMIN")
                .build();

        when(projectMemberService.getProjectMember(any(),any())).thenReturn(projectMemberDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/accounts/{accountId}",1,"admin")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auth").value(projectMemberDto.getProjectMemberRole()));

    }

    @Test
    @Order(3)
    void getProjects() throws Exception{
        ProjectDto projectDto1 = ProjectDto.builder()
                .projectContent("content")
                .projectCreatedAt(LocalDateTime.now())
                .projectMemberId("admin")
                .projectTitle("title")
                .projectMemberRole("ROLE_ADMIN")
                .statusCodeName("활성")
                .projectSeq(1)
                .build();

        ProjectDto projectDto2 = ProjectDto.builder()
                .projectContent("content-2")
                .projectCreatedAt(LocalDateTime.now())
                .projectMemberId("admin")
                .projectTitle("title-2")
                .projectMemberRole("ROLE_MEMBER")
                .statusCodeName("활성")
                .projectSeq(2)
                .build();

        List<ProjectDto> projects = List.of(projectDto1, projectDto2);

        when(projectMemberService.getProjects(any())).thenReturn(projects);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/accounts/{accountId}","admin")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].member").value("admin"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].member").value("admin"))
                .andExpect(jsonPath("$[1].id").value(2));


    }

    @Test
    @Order(4)
    void createProjectMember() throws Exception{
        MemberListDto memberListDto1 = MemberListDto.builder()
                .projectMemberSeq(2)
                .build();

        MemberListDto memberListDto2 = MemberListDto.builder()
                .projectMemberSeq(3)
                .build();

        List<MemberListDto> memberList = List.of(memberListDto1, memberListDto2);

        Member member1 = Member.builder()
                .projectMemberId("member1")
                .build();

        Member member2 = Member.builder()
                .projectMemberId("member2")
                .build();

        List<Member> members = List.of(member1, member2);

        ProjectMemberRegisterRequest request = ProjectMemberRegisterRequest.builder()
                .projectMemberIds(members)
                .build();

        when(projectMemberService.createProjectMember(any(), any(), any())).thenReturn(memberList);

        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/accounts/{accountId}", 1, "admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].member-id").value(2))
                .andExpect(jsonPath("$[1].member-id").value(3));
    }

    @Test
    @Order(5)
    void createProjectMemberNullTest() throws Exception{

        Member member1 = Member.builder().projectMemberId(null).build();

        Member member2 = Member.builder().projectMemberId("").build();

        List<Member> members = List.of(member1, member2);

        ProjectMemberRegisterRequest request = ProjectMemberRegisterRequest.builder()
                .projectMemberIds(members)
                .build();

        when(projectMemberService.createProjectMember(request, 1, "admin")).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> projectMemberService.createProjectMember(request, 1,"admin"));

    }

    @Test
    @Order(6)
    void deleteProjectMember() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/projects/{projectId}/accounts/{accountId}", 1, "member1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}