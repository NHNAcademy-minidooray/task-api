package com.nhnacademy.minidooray.taskapi.repository.tag;

import com.nhnacademy.minidooray.taskapi.domain.response.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.repository.milestone.MilestoneRepository;
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
class TagRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TagRepository tagRepository;

    Project project;
    StatusCode statusCode;
    ProjectMember projectMember;
    Tag tag;

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

        tag = Tag.builder()
                .tagName("tag-name")
                .project(project)
                .build();
    }

    @Test
    @Order(1)
    void existsByTagName() {
        tagRepository.save(tag);

        Boolean actual = tagRepository.existsByTagName("tag-name");
        assertThat(actual).isTrue();
    }

    @Test
    @Order(2)
    void findByTagName() {
        tagRepository.save(tag);

        Tag actual = tagRepository.findByTagName("tag-name").get();
        assertThat(actual.getTagName()).isEqualTo("tag-name");
    }

    @Test
    @Order(3)
    void getTags() {
        Tag tag2 = Tag.builder()
                .tagName("tag-name2")
                .project(project)
                .build();

        tagRepository.save(tag);
        tagRepository.save(tag2);

        List<TagDto> actual = tagRepository.getTags(project.getProjectSeq());
        assertThat(actual.get(1).getTagName()).isEqualTo("tag-name2");
    }

    @Test
    @Order(4)
    void getTag() {
        tagRepository.save(tag);

        TagDto actual = tagRepository.getTag(tag.getTagSeq());

        assertThat(actual.getTagName()).isEqualTo("tag-name");
    }

    @Test
    @Order(5)
    void getTasks() {
        tagRepository.save(tag);

        Task task = Task.builder()
                .taskTitle("task-title")
                .taskContent("task-content")
                .taskCreatedAt(LocalDateTime.now())
                .milestone(null)
                .projectMember(projectMember)
                .build();

        entityManager.persist(task);

        TaskTag taskTag = TaskTag.builder()
                .pk(TaskTag.Pk.builder()
                        .taskSeq(task.getTaskSeq())
                        .tagSeq(tag.getTagSeq())
                        .build())
                .tag(tag)
                .task(task)
                .build();

        entityManager.persist(taskTag);

        List<TaskListDto> actual = tagRepository.getTasks(project.getProjectSeq(), tag.getTagSeq());
        assertThat(actual.get(0).getTaskTitle()).isEqualTo("task-title");

    }

    @Test
    @Order(6)
    void save() {
        Tag tag2 = Tag.builder()
                .tagName("tag-name2")
                .project(project)
                .build();

        tagRepository.save(tag2);

        Tag actual = tagRepository.findById(tag2.getTagSeq()).get();
        assertThat(actual.getTagName()).isEqualTo("tag-name2");
    }

    @Test
    @Order(7)
    void update() {
        tagRepository.save(tag);

        tag.update("tag-update");

        Tag actual = tagRepository.findById(tag.getTagSeq()).get();
        assertThat(actual.getTagName()).isEqualTo("tag-update");
    }

}