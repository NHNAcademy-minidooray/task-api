package com.nhnacademy.minidooray.taskapi.repository.milestone;

import com.nhnacademy.minidooray.taskapi.domain.response.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MilestoneRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MilestoneRepository milestoneRepository;

    Project project;
    StatusCode statusCode;
    ProjectMember projectMember;
    Milestone milestone;


    @BeforeEach
    void setUp() {
        statusCode = StatusCode.builder()
                .statusCodeName("활성")
                .build();

        entityManager.persist(statusCode);


        project = Project.builder()
                .projectTitle("title")
                .projectContent("content")
                .projectCreatedAt(LocalDateTime.now())
                .statusCode(statusCode)
                .build();

        entityManager.persist(project);

        projectMember = ProjectMember.builder()
                .projectMemberId("test")
                .projectMemberRole("ROLE_ADMIN")
                .project(project)
                .build();

        entityManager.persist(projectMember);

        milestone = Milestone.builder()
                .project(project)
                .milestoneName("milestone")
                .milestoneStartPeriod(null)
                .milestoneEndOfPeriod(null)
                .build();
    }

    @Test
    @Order(1)
    void findByMilestoneName() {
        milestoneRepository.save(milestone);

        Milestone actual = milestoneRepository.findByMilestoneName("milestone").get();

        assertThat(actual.getMilestoneName()).isEqualTo("milestone");
    }

    @Test
    @Order(2)
    void getMilestones() {
        Milestone milestone2 = Milestone.builder()
                .project(project)
                .milestoneName("milestone2")
                .milestoneStartPeriod(null)
                .milestoneEndOfPeriod(null)
                .build();

        milestoneRepository.save(milestone);
        milestoneRepository.save(milestone2);

        List<MilestoneDto> actual = milestoneRepository.getMilestones(project.getProjectSeq());

        assertThat(actual.get(1).getName()).isEqualTo("milestone2");

    }

    @Test
    @Order(3)
    void getMilestone() {
        milestoneRepository.save(milestone);

        MilestoneDto actual = milestoneRepository.getMileStone(milestone.getMilestoneSeq()).get();

        assertThat(actual.getName()).isEqualTo("milestone");
    }

    @Test
    @Order(4)
    void getTasks() {
        milestoneRepository.save(milestone);


        Task task = Task.builder()
                .taskTitle("task-title")
                .taskContent("task-content")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(milestone)
                .projectMember(projectMember)
                .build();

        entityManager.persist(task);

        List<TaskListDto> actual = milestoneRepository
                .getTasks(projectMember.getProject().getProjectSeq(), milestone.getMilestoneSeq());

        assertThat(actual.get(0).getTaskTitle()).isEqualTo("task-title");
    }
}