package com.nhnacademy.minidooray.taskapi.repository.project;

import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.StatusCode;
import com.nhnacademy.minidooray.taskapi.repository.comment.CommentRepository;
import com.nhnacademy.minidooray.taskapi.repository.projectmember.ProjectMemberRepository;
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
class ProjectRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProjectRepository projectRepository;

    Project project;
    StatusCode statusCode;
    ProjectMember projectMember;

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

        projectMember = ProjectMember.builder()
                .projectMemberId("test")
                .projectMemberRole("ROLE_ADMIN")
                .project(project)
                .build();
    }

    @Test
    @Order(1)
    void testFindAllBy() {
        projectRepository.save(project);
        entityManager.persist(projectMember);

        List<ProjectDto> actual = projectRepository.findAllBy();
        assertThat(actual.get(0).getProjectMemberId()).isEqualTo("test");
    }

    @Test
    @Order(2)
    void testFindByProjectSeq() {
        projectRepository.save(project);
        entityManager.persist(projectMember);

        ProjectDto actual = projectRepository.findByProjectSeq(project.getProjectSeq());
        assertThat(actual.getProjectTitle()).isEqualTo("title");
    }

    @Test
    @Order(3)
    void save() {
        Project project1 = Project.builder()
                .statusCode(statusCode)
                .projectCreatedAt(LocalDateTime.now())
                .projectContent("content2")
                .projectTitle("title2")
                .build();

        projectRepository.save(project1);

        Project actual = projectRepository.findById(project1.getProjectSeq()).get();
        assertThat(actual.getProjectContent()).isEqualTo("content2");
    }


    @Test
    @Order(4)
    void update() {
        projectRepository.save(project);
        entityManager.persist(projectMember);

        project.update("update-title","update-content");

        Project actual = projectRepository.findById(project.getProjectSeq()).get();
        assertThat(actual.getProjectTitle()).isEqualTo("update-title");
    }



}