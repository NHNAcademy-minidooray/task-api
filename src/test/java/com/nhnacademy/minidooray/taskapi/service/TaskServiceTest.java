package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.request.task.TaskRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.nhnacademy.minidooray.taskapi.exception.BadRequestException;
import com.nhnacademy.minidooray.taskapi.exception.ForbiddenException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.milestone.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.projectmember.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.tag.TagRepository;
import com.nhnacademy.minidooray.taskapi.repository.task.TaskRepository;
import com.nhnacademy.minidooray.taskapi.repository.tasktag.TaskTagRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMemberRepository projectMemberRepository;
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TaskTagRepository taskTagRepository;

    TaskRegisterRequest registerRequest;
    @BeforeEach
    void setUp() {
        registerRequest = TaskRegisterRequest.builder()
                .title("test")
                .content("test")
                .milestoneName("test")
                .build();
    }

    @Test
    @Order(1)
    void getProjectTasksNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.getProjectTasks(1));
    }

    @Test
    @Order(2)
    void getProjectTasksTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        List<TaskListDto> testList = List.of(mock(TaskListDto.class));

        when(taskRepository.getProjectTasks(anyInt()))
                .thenReturn(testList);

        List<TaskListDto> actual = taskService.getProjectTasks(1);
        assertThat(actual.size())
                .isEqualTo(testList.size());
    }

    @Test
    @Order(3)
    void getTaskProjectNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.getTask(1, 1));
    }

    @Test
    @Order(4)
    void getTaskTaskNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.getTask(1, 1));
    }

    @Test
    @Order(5)
    void getTaskTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));

        TaskDto actual = taskRepository.getTask(1, 1);
        assertThat(taskService.getTask(1, 1))
                .isEqualTo(actual);
    }

    @Test
    @Order(6)
    void getTasksProjectNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.getTasks(1, "test"));
    }

    @Test
    @Order(7)
    void getTasksNotThisProjectMemberExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(projectMemberRepository.getProjectMember(anyInt(), anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.getTasks(1, "test"));
    }

    @Test
    @Order(8)
    void getTasksTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(projectMemberRepository.getProjectMember(anyInt(), anyString()))
                .thenReturn(Optional.of(mock(ProjectMemberDto.class)));

        List<TaskListDto> testList = List.of(mock(TaskListDto.class));
        when(taskRepository.getTasks(anyInt(), anyString()))
                .thenReturn(testList);

        List<TaskListDto> actual = taskService.getTasks(1,"test");
        assertThat(actual.size())
                .isEqualTo(testList.size());
    }

//    @Test
    @Order(9)
    void getTaskAll() {
        when(projectMemberRepository.findByProjectMemberId(anyString()))
                .thenReturn(List.of(mock(ProjectMember.class)));

        List<TaskListDto> testList = List.of(mock(TaskListDto.class));
        when(taskRepository.getProjectTasks(anyInt()))
                .thenReturn(testList);

        List<TaskListDto> actual = taskService.getTaskAll("test");
        assertThat(actual.size())
                .isEqualTo(testList.size());
    }

    @Test
    @Order(10)
    void createTaskProjectNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.createTask(registerRequest, 1, "test"));
    }

    @Test
    @Order(11)
    void createTaskForbiddenExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(ForbiddenException.class,
                () -> taskService.createTask(registerRequest, 1, "test"));
    }

    @Test
    @Order(12)
    void createTaskMilestoneNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        TaskRegisterRequest request = mock(TaskRegisterRequest.class);
        when(request.getMilestoneName())
                .thenReturn("test");
        when(milestoneRepository
                .findByMilestoneName(request.getMilestoneName()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.createTask(request, 1, "test"));
    }

//    @Test
    @Order(13)
    void createTaskTagNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        TaskRegisterRequest request = mock(TaskRegisterRequest.class);
        when(request.getMilestoneName())
                .thenReturn("test");
        when(milestoneRepository
                .findByMilestoneName(request.getMilestoneName()))
                .thenReturn(Optional.of(mock(Milestone.class)));

        when(request.getTagNames().size())
                .thenReturn(1);
        when(tagRepository.existsByTagName(anyString()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.createTask(request, 1, "test"));
    }

//    @Test
    @Order(14)
    void createTaskTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        ProjectMember projectMember = projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt());
        when(projectMember)
                .thenReturn(mock(ProjectMember.class));
        TaskRegisterRequest request = mock(TaskRegisterRequest.class);
        when(request.getMilestoneName())
                .thenReturn("test");
        when(milestoneRepository
                .findByMilestoneName(request.getMilestoneName()))
                .thenReturn(Optional.of(mock(Milestone.class)));

        Task taskActual = Task.builder()
                .taskTitle("test")
                .taskContent("test")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(mock(Milestone.class))
                .projectMember(mock(ProjectMember.class))
                .build();

        when(taskRepository.save(any(Task.class)))
                .thenReturn(taskActual);
        when(taskRepository.getTask(projectMember.getProject().getProjectSeq(), taskActual.getTaskSeq()))
                .thenReturn(mock(TaskDto.class));

        assertThat(taskService.createTask(request, 1, "test"))
                .isInstanceOf(TaskDto.class);
    }

    @Test
    @Order(15)
    void modifyTask() {
    }

    @Test
    @Order(16)
    void deleteTaskProjectNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.deleteTask(1, 1, "test"));
    }

    @Test
    @Order(17)
    void deleteTaskTaskNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> taskService.deleteTask(1, 1, "test"));
    }

    @Test
    @Order(18)
    void deleteTaskBadRequestExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));
        when(taskRepository.getWriter(anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(BadRequestException.class,
                () -> taskService.deleteTask(1, 1, "test"));
    }

    @Test
    @Order(19)
    void deleteTaskForbiddenExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));
        when(taskRepository.getWriter(anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository.getWriter(anyInt()).getProjectMemberId())
                .thenReturn("testNo");

        Assertions.assertThrows(ForbiddenException.class,
                () -> taskService.deleteTask(1, 1, "test"));
    }

    @Test
    @Order(20)
    void deleteTaskTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(taskRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));
        when(taskRepository.getWriter(anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository.getWriter(anyInt()).getProjectMemberId())
                .thenReturn("test");

        taskService.deleteTask(1, 1, "test");
        verify(taskRepository, times(1)).delete(any());
    }
}