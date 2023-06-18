package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.request.milestone.MilestoneModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.milestone.MilestoneRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.milestone.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MilestoneServiceTest {

    @InjectMocks
    private MilestoneService milestoneService;
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private ProjectRepository projectRepository;

    Milestone milestoneActual;
    MilestoneRegisterRequest registerRequest;
    MilestoneModifyRequest modifyRequest;

    @BeforeEach
    void setUp() {
        milestoneActual = Milestone.builder()
                .milestoneName("test")
                .milestoneStartPeriod(LocalDate.now())
                .milestoneEndOfPeriod(LocalDate.now())
                .build();

        registerRequest = MilestoneRegisterRequest.builder()
                .name("test")
                .start(LocalDate.now())
                .end(LocalDate.now())
                .build();

        modifyRequest = MilestoneModifyRequest.builder()
                .name("test")
                .start(LocalDate.now())
                .end(LocalDate.now())
                .build();
    }

    @Test
    @Order(1)
    void getMilestonesExceptionTest() {
        when(projectRepository.existsById(any()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.getMilestones(1));
    }
    @Test
    @Order(2)
    void getMilestonesTest() {
        when(projectRepository.existsById(any()))
                .thenReturn(true);

        List<MilestoneDto> testList = List.of(mock(MilestoneDto.class));

        when(milestoneRepository.getMilestones(anyInt()))
                .thenReturn(testList);

        List<MilestoneDto> actual = milestoneService.getMilestones(anyInt());
        assertThat(actual.size()).isEqualTo(testList.size());
    }

    @Test
    @Order(3)
    void getMilestoneProjectNotFoundExceptionTest() {
        when(projectRepository.existsById(any()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.getMilestone(1, 1));
    }

    @Test
    @Order(4)
    void getMilestoneMilestoneNotFoundExceptionTest() {
        when(projectRepository.existsById(any()))
                .thenReturn(true);
        when(milestoneRepository.getMileStone(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.getMilestone(1, 1));
    }

    @Test
    @Order(5)
    void getMilestoneTest() {
        when(projectRepository.existsById(any()))
                .thenReturn(true);
        when(milestoneRepository.getMileStone(anyInt()))
                .thenReturn(Optional.of(mock(MilestoneDto.class)));

        MilestoneDto actual = milestoneService.getMilestone(1, 1);
        assertThat(actual).isInstanceOf(MilestoneDto.class);
    }

    @Test
    @Order(6)
    void getTasksProjectNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.getTasks(1, 1));
    }

    @Test
    @Order(7)
    void getTasksMilestoneNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(milestoneRepository.getMileStone(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.getTasks(1, 1));
    }

    @Test
    @Order(8)
    void getTasksTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(milestoneRepository.getMileStone(anyInt()))
                .thenReturn(Optional.of(mock(MilestoneDto.class)));

        List<TaskListDto> testList = List.of(mock(TaskListDto.class));

        when(milestoneRepository.getTasks(anyInt(), anyInt()))
                .thenReturn(testList);

        List<TaskListDto> actual = milestoneService.getTasks(1, 1);
        assertThat(actual.size()).isEqualTo(testList.size());
    }

    @Test
    @Order(9)
    void createMilestoneProjectNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.createMilestone(registerRequest, 1));
    }

    @Test
    @Order(10)
    void createMilestoneMilestoneNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.save(any(Milestone.class)))
                .thenReturn(milestoneActual);
        when(milestoneRepository.getMileStone(milestoneActual.getMilestoneSeq()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.createMilestone(registerRequest, 1));
    }

    @Test
    @Order(11)
    void createMilestoneTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.save(any(Milestone.class)))
                .thenReturn(milestoneActual);
        when(milestoneRepository.getMileStone(milestoneActual.getMilestoneSeq()))
                .thenReturn(Optional.of(mock(MilestoneDto.class)));

        assertThat(milestoneService.createMilestone(registerRequest,1))
                .isInstanceOf(MilestoneDto.class);
    }

    @Test
    @Order(12)
    void modifyMilestoneProjectNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.modifyMilestone(modifyRequest, 1, 1));
    }

    @Test
    @Order(13)
    void modifyMilestoneMilestoneNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.modifyMilestone(modifyRequest, 1, 1));
    }

    @Test
    @Order(14)
    void modifyMilestoneNotThisProjectExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Milestone.class)));
        when(milestoneRepository.getMileStone(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.modifyMilestone(modifyRequest, 1, 1));
    }

    @Test
    @Order(15)
    void modifyMilestoneTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Milestone.class)));
        when(milestoneRepository.getMileStone(anyInt()))
                .thenReturn(Optional.of(mock(MilestoneDto.class)));

        assertThat(milestoneService.modifyMilestone(modifyRequest, 1, 1))
                .isInstanceOf(MilestoneDto.class);
    }

    @Test
    @Order(16)
    void deleteMilestoneProjectNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.deleteMilestone(1, 1));
    }

    @Test
    @Order(17)
    void deleteMilestoneMilestoneNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.deleteMilestone(1, 1));
    }

    @Test
    @Order(18)
    void deleteMilestoneNotThisProjectExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Milestone.class)));
        when(milestoneRepository.getMileStone(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> milestoneService.deleteMilestone(1, 1));
    }

    @Test
    @Order(19)
    void deleteMilestoneTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));
        when(milestoneRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Milestone.class)));
        when(milestoneRepository.getMileStone(anyInt()))
                .thenReturn(Optional.of(mock(MilestoneDto.class)));

        milestoneService.deleteMilestone(1, 1);
        verify(milestoneRepository, times(1)).delete(any());
    }
}