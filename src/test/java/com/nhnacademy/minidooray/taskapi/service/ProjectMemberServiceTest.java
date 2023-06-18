package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.request.projectmember.Member;
import com.nhnacademy.minidooray.taskapi.domain.request.projectmember.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.MemberListDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.exception.ForbiddenException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.projectmember.ProjectMemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectMemberServiceTest {

    @InjectMocks
    private ProjectMemberService projectMemberService;
    @Mock
    private ProjectMemberRepository projectMemberRepository;
    @Mock
    private ProjectRepository projectRepository;

    ProjectMemberRegisterRequest registerRequest;
    @BeforeEach
    void setUp() {
        registerRequest = ProjectMemberRegisterRequest.builder()
                .projectMemberIds(List.of(mock(Member.class)))
                .build();
    }

    @Test
    @Order(1)
    void getProjectMembersNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> projectMemberService.getProjectMembers(1));
    }

    @Test
    @Order(2)
    void getProjectMembersTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        List<ProjectMemberDto> testList = List.of(mock(ProjectMemberDto.class));

        when(projectMemberRepository.getProjectMemberList(anyInt()))
                .thenReturn(testList);

        List<ProjectMemberDto> actual = projectMemberService.getProjectMembers(anyInt());
        assertThat(actual.size())
                .isEqualTo(testList.size());
    }

    @Test
    @Order(3)
    void getProjectMemberNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> projectMemberService.getProjectMember(1, "test"));
    }

    @Test
    @Order(4)
    void getProjectMemberForbiddenExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        when(projectMemberRepository.findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(ForbiddenException.class,
                () -> projectMemberService.getProjectMember(1, "test"));
    }

    @Test
    @Order(5)
    void getProjectMemberTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        when(projectMemberRepository.findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));

        when(projectMemberRepository.getProjectMember(anyInt(), anyString()))
                .thenReturn(Optional.of(mock(ProjectMemberDto.class)));

        ProjectMemberDto actual = projectMemberRepository.getProjectMember(1, "test").get();

        assertThat(actual)
                .isEqualTo(projectMemberService.getProjectMember(1, "test"));
    }

    @Test
    @Order(6)
    void getProjectsEmptyProjectListTest() {
        when(projectMemberRepository.findByProjectMemberId(anyString()))
                .thenReturn(Collections.emptyList());

        List<ProjectMember> actual = projectMemberRepository.findByProjectMemberId("test");

        assertThat(actual)
                .isEqualTo(projectMemberService.getProjects("test"));
    }

    @Test
    @Order(7)
    void getProjectsTest() {
        when(projectMemberRepository.findByProjectMemberId(anyString()))
                .thenReturn(List.of(mock(ProjectMember.class)));

        List<ProjectDto> testList = List.of(mock(ProjectDto.class));

        when(projectMemberRepository.getProjects(anyString()))
                .thenReturn(testList);

        List<ProjectDto> actual = projectMemberService.getProjects(anyString());

        assertThat(actual.size())
                .isEqualTo(testList.size());
    }

    @Test
    @Order(8)
    void createProjectMemberNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> projectMemberService.createProjectMember(registerRequest, 1, "test"));
    }

    @Test
    @Order(9)
    void createProjectMemberForbiddenExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        ProjectMember projectMember = mock(ProjectMember.class);
        when(projectMember.getProjectMemberId()).thenReturn("testException");

        when(projectMemberRepository.findByProject_ProjectSeqAndProjectMemberRole(anyInt(), anyString()))
                .thenReturn(projectMember);

        Assertions.assertThrows(ForbiddenException.class,
                () -> projectMemberService.createProjectMember(registerRequest, 1, "test"));
    }

    @Test
    @Order(10)
    void createProjectMemberTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        ProjectMember projectMember = mock(ProjectMember.class);
        when(projectMember.getProjectMemberId()).thenReturn("test");

        when(projectMemberRepository.findByProject_ProjectSeqAndProjectMemberRole(anyInt(), anyString()))
                .thenReturn(projectMember);

        List<MemberListDto> testList = List.of(mock(MemberListDto.class));

        List<MemberListDto> actual = projectMemberService.createProjectMember(registerRequest, 1, "test");
        assertThat(actual.size())
                .isEqualTo(testList.size());
    }

    @Test
    @Order(11)
    void deleteProjectMemberNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> projectMemberService.deleteProjectMember(1,"test"));
    }

    @Test
    @Order(12)
    void deleteProjectMemberForbiddenExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(ForbiddenException.class,
                () -> projectMemberService.deleteProjectMember(1,"test"));
    }

//    @Test
    @Order(13)
    void deleteProjectMemberForAdminTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        ProjectMember projectMember = projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt());
        when(projectMember)
                .thenReturn(mock(ProjectMember.class));

        when(projectMember.getProjectMemberRole())
                .thenReturn("ROLE_ADMIN");

        projectMemberService.deleteProjectMember(1, "test");
        verify(projectMemberRepository, times(1)).deleteById(anyInt());
        verify(projectRepository, times(1)).deleteById(anyInt());
    }

    @Test
    @Order(14)
    void deleteProjectMemberForAccountTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        ProjectMember projectMember = mock(ProjectMember.class);
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(projectMember);

        when(projectMember.getProjectMemberRole())
                .thenReturn("test");


        projectMemberService.deleteProjectMember(1, "test");
        verify(projectMemberRepository, times(1)).deleteById(anyInt());
    }
}