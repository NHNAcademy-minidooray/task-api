package com.nhnacademy.minidooray.taskapi.repository.task;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
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
class TaskRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TaskRepository taskRepository;

    Project project;
    StatusCode statusCode;
    ProjectMember projectMember;
    Milestone milestone;
    Tag tag;
    TaskTag taskTag;
    Task task;

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

    }

    @Test
    @Order(1)
    void findByTaskSeqAndProjectMember_Project_ProjectSeq() {
        taskRepository.save(task);

        Task actual = taskRepository
                .findByTaskSeqAndProjectMember_Project_ProjectSeq(task.getTaskSeq(), project.getProjectSeq()).get();

        assertThat(actual.getTaskTitle()).isEqualTo(task.getTaskTitle());
    }

    @Test
    @Order(2)
    void getProjectTasks() {
        Task task2 = Task.builder()
                .taskTitle("task-title2")
                .taskContent("task-content2")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(null)
                .projectMember(projectMember)
                .build();

        taskRepository.save(task);
        taskRepository.save(task2);

        List<TaskListDto> actual = taskRepository.getProjectTasks(project.getProjectSeq());
        assertThat(actual.get(0).getTaskTitle()).isEqualTo("task-title2"); //내림차순
    }

    @Test
    @Order(3)
    void getTask() {
        taskRepository.save(task);

        TaskDto actual = taskRepository
                .getTask(project.getProjectSeq(), task.getTaskSeq());

        assertThat(actual.getTaskTitle()).isEqualTo(task.getTaskTitle());
    }

    @Test
    @Order(4)
    void getTasks() {
        Task task2 = Task.builder()
                .taskTitle("task-title2")
                .taskContent("task-content2")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(null)
                .projectMember(projectMember)
                .build();

        taskRepository.save(task);
        taskRepository.save(task2);

        List<TaskListDto> actual = taskRepository.getTasks(project.getProjectSeq(), projectMember.getProjectMemberId());
        assertThat(actual.get(1).getTaskTitle()).isEqualTo("task-title"); //내림차순
    }

    @Test
    @Order(5)
    void getWriter() {
        taskRepository.save(task);

        ProjectMember actual = taskRepository.getWriter(task.getTaskSeq());
        assertThat(actual.getProjectMemberId()).isEqualTo("test");
    }

    @Test
    @Order(6)
    void getTaskAll() {
        Task task2 = Task.builder()
                .taskTitle("task-title2")
                .taskContent("task-content2")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(null)
                .projectMember(projectMember)
                .build();

        taskRepository.save(task);
        taskRepository.save(task2);

        List<TaskListDto> actual = taskRepository.getTaskAll(projectMember.getProjectMemberId());
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    @Order(7)
    void save() {
        taskRepository.save(task);
        Task actual = taskRepository.findById(task.getTaskSeq()).get();
        assertThat(actual.getTaskContent()).isEqualTo(task.getTaskContent());
    }

    @Test
    @Order(8)
    void update1() {
        milestone = Milestone.builder()
                .project(project)
                .milestoneName("milestone")
                .milestoneStartPeriod(null)
                .milestoneEndOfPeriod(null)
                .build();

        entityManager.persist(milestone);

        Task task2 = Task.builder()
                .taskTitle("task-title2")
                .taskContent("task-content2")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(null)
                .projectMember(projectMember)
                .build();

        taskRepository.save(task2);

        task2.update("title-update","content-update",milestone);

        Task actual = taskRepository.findById(task2.getTaskSeq()).get();
        assertThat(actual.getMilestone().getMilestoneName()).isEqualTo("milestone");

    }

    @Test
    @Order(9)
    void update2() {

        Task task2 = Task.builder()
                .taskTitle("task-title2")
                .taskContent("task-content2")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(null)
                .projectMember(projectMember)
                .build();

        taskRepository.save(task2);

        tag = Tag.builder()
                .tagName("tag-name")
                .project(project)
                .build();
        entityManager.persist(tag);

        Tag tag2 = Tag.builder()
                .tagName("tag-name2")
                .project(project)
                .build();
        entityManager.persist(tag2);

        taskTag = TaskTag.builder()
                .pk(TaskTag.Pk.builder()
                        .taskSeq(task2.getTaskSeq())
                        .tagSeq(tag.getTagSeq())
                        .build())
                .tag(tag)
                .task(task2)
                .build();
        entityManager.persist(taskTag);

        TaskTag taskTag2 = taskTag = TaskTag.builder()
                .pk(TaskTag.Pk.builder()
                        .taskSeq(task2.getTaskSeq())
                        .tagSeq(tag2.getTagSeq())
                        .build())
                .tag(tag2)
                .task(task2)
                .build();
        entityManager.persist(taskTag2);

        TaskDto actual = taskRepository.getTask(project.getProjectSeq(), task2.getTaskSeq());

        assertThat(actual.getTagNames().size()).isEqualTo(2);
        assertThat(actual.getTagNames().get(0)).isEqualTo("tag-name");


    }

}