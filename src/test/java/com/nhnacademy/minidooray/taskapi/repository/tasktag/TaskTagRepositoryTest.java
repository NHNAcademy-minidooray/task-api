package com.nhnacademy.minidooray.taskapi.repository.tasktag;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.repository.comment.CommentRepository;
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
class TaskTagRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TaskTagRepository taskTagRepository;

    Project project;
    StatusCode statusCode;
    ProjectMember projectMember;
    Task task;
    Tag tag;
    TaskTag taskTag;

    @BeforeEach
    void setUp() {

        statusCode = StatusCode.builder()
                .statusCodeName("활성")
                .build();

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

        tag = Tag.builder()
                .tagName("tag-name")
                .project(project)
                .build();

        task = Task.builder()
                .taskTitle("task-title")
                .taskContent("task-content")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(null)
                .projectMember(projectMember)
                .build();


        entityManager.persist(statusCode);
        entityManager.persist(project);
        entityManager.persist(projectMember);
        entityManager.persist(tag);
        entityManager.persist(task);

        taskTag = TaskTag.builder()
                .pk(TaskTag.Pk.builder()
                        .taskSeq(task.getTaskSeq())
                        .tagSeq(tag.getTagSeq())
                        .build())
                .tag(tag)
                .task(task)
                .build();
    }

    @Test
    @Order(1)
    void findByTask_TaskSeq() {
        taskTagRepository.save(taskTag);

        List<TaskTag> actual = taskTagRepository.findByTask_TaskSeq(task.getTaskSeq());
        assertThat(actual.get(0).getTask().getTaskTitle()).isEqualTo(task.getTaskTitle());
    }

    @Test
    @Order(2)
    void getTagsByTaskSeq() {
        taskTagRepository.save(taskTag);

        List<TaskTagDto> actual = taskTagRepository.getTagsByTaskSeq(task.getTaskSeq());
        assertThat(actual.get(0).getTaskSeq()).isEqualTo(task.getTaskSeq());
    }

    @Test
    @Order(3)
    void save() {
        Tag tag2 = Tag.builder()
                .tagName("tag-name2")
                .project(project)
                .build();

        TaskTag taskTag2 = TaskTag.builder()
                .pk(TaskTag.Pk.builder()
                        .taskSeq(task.getTaskSeq())
                        .tagSeq(tag2.getTagSeq())
                        .build())
                .tag(tag2)
                .task(task)
                .build();
        entityManager.persist(tag2);
        taskTagRepository.save(taskTag);
        taskTagRepository.save(taskTag2);

        List<TaskTag> actual = taskTagRepository.findByTask_TaskSeq(task.getTaskSeq());
        assertThat(actual.get(1).getTag().getTagName()).isEqualTo(tag2.getTagName());
    }
}