package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.request.project.ProjectModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.project.ProjectRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.StatusCode;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.StatusCodeRepository;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.projectmember.ProjectMemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private StatusCodeRepository statusCodeRepository;
    @Mock
    private ProjectMemberRepository projectMemberRepository;

    ProjectRegisterRequest registerRequest;
    ProjectModifyRequest modifyRequest;
    @BeforeEach
    void setUp() {
        registerRequest = ProjectRegisterRequest.builder()
                .projectTitle("test")
                .projectContent("test")
                .build();

        modifyRequest = ProjectModifyRequest.builder()
                .projectTitle("test")
                .projectContent("test")
                .build();
    }

    @Test
    @Order(1)
    void getProjectsTest() {
        List<ProjectDto> testList = List.of(mock(ProjectDto.class));

        when(projectRepository.findAllBy())
                .thenReturn(testList);

        List<ProjectDto> actual = projectService.getProjects();

        assertThat(actual.size())
                .isEqualTo(testList.size());
    }

    @Test
    @Order(2)
    void getProjectTest() {
        when(projectRepository.findByProjectSeq(any()))
                .thenReturn(mock(ProjectDto.class));

        ProjectDto actual = projectService.getProject(1);
        assertThat(actual)
                .isInstanceOf(ProjectDto.class);
    }

    @Test
    @Order(3)
    void createProjectTest() {
        when(statusCodeRepository.getReferenceById(anyInt()))
                .thenReturn(mock(StatusCode.class));
        when(projectMemberRepository.save(any()))
                .thenReturn(mock(ProjectMember.class));

        projectService.createProject(registerRequest, "test");
        verify(projectRepository, times(1)).findByProjectSeq(any());
    }

    @Test
    @Order(4)
    void modifyExceptionProjectTest() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> projectService.modifyProject(modifyRequest, 1));
    }

    @Test
    @Order(5)
    void modifyProjectTest() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(projectRepository.findByProjectSeq(any()))
                .thenReturn(mock(ProjectDto.class));

        ProjectDto actual = projectService.modifyProject(modifyRequest, 1);
        assertThat(actual)
                .isInstanceOf(ProjectDto.class);
    }

    @Test
    @Order(6)
    void deleteProjectExceptionTest() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> projectService.deleteProject(1));
    }

    @Test
    @Order(7)
    void deleteProjectTest() {
        when(projectRepository.findById(any()))
                .thenReturn(Optional.of(mock(Project.class)));

        projectService.deleteProject(any());
        verify(projectRepository, times(1)).deleteById(any());
    }
}