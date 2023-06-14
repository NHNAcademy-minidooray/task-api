package com.nhnacademy.minidooray.taskapi.repository.projectmember;

import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.StatusCode;
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
class ProjectMemberRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProjectMemberRepository projectMemberRepository;

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

        entityManager.persist(project);

        projectMember = ProjectMember.builder()
                .projectMemberId("test")
                .projectMemberRole("ROLE_ADMIN")
                .project(project)
                .build();
    }

    @Test
    @Order(1)
    void findByProjectMemberIdAndProject_ProjectSeq() {
        projectMemberRepository.save(projectMember);

        ProjectMember actual = projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(projectMember.getProjectMemberId(),
                                                            project.getProjectSeq()).get();

        assertThat(actual.getProjectMemberId()).isEqualTo("test");
    }

    @Test
    @Order(2)
    void findByProject_ProjectSeqAndProjectMemberRole() {
        projectMemberRepository.save(projectMember);

        ProjectMember actual = projectMemberRepository
                .findByProject_ProjectSeqAndProjectMemberRole(project.getProjectSeq(), "ROLE_ADMIN");

        assertThat(actual.getProject().getProjectTitle()).isEqualTo("title");
    }

    @Test
    @Order(3)
    void getProjectMemberList() {
        projectMemberRepository.save(projectMember);

        List<ProjectMemberDto> actual = projectMemberRepository.getProjectMemberList(project.getProjectSeq());

        assertThat(actual.get(0).getProjectMemberRole()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    @Order(4)
    void getProjectMember() {
        projectMemberRepository.save(projectMember);

        ProjectMemberDto actual = projectMemberRepository
                .getProjectMember(project.getProjectSeq(), projectMember.getProjectMemberId()).get();

        assertThat(actual.getProjectMemberId()).isEqualTo("test");
    }

    @Test
    @Order(5)
    void getProjects() {
        projectMemberRepository.save(projectMember);

        List<ProjectDto> actual = projectMemberRepository.getProjects("test");

        assertThat(actual.size()).isEqualTo(1);
    }

    @Test
    @Order(6)
    void save() {
        ProjectMember projectMember1 = ProjectMember.builder()
                .project(project)
                .projectMemberRole("ROLE_MEMBER")
                .projectMemberId("test2")
                .build();

        projectMemberRepository.save(projectMember1);

        ProjectMember actual = projectMemberRepository.findById(projectMember1.getProjectMemberSeq()).get();

        assertThat(actual.getProjectMemberId()).isEqualTo("test2");
    }


}