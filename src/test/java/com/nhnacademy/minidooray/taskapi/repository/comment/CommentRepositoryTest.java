package com.nhnacademy.minidooray.taskapi.repository.comment;

import com.nhnacademy.minidooray.taskapi.domain.response.CommentDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
class CommentRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CommentRepository commentRepository;

    Project project;
    StatusCode statusCode;
    ProjectMember projectMember;
    Task task;
    Comment comment;

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
        entityManager.persist(task);

        comment = Comment.builder()
                .projectMember(projectMember)
                .task(task)
                .commentContent("test-comment")
                .build();
    }

    @Test
    @Order(1)
    void findByCommentSeqAndTask_TaskSeq() {
        commentRepository.save(comment);

        Comment actual = commentRepository.findByCommentSeqAndTask_TaskSeq(comment.getCommentSeq(), task.getTaskSeq()).get();
        assertThat(actual.getCommentContent()).isEqualTo(comment.getCommentContent());
    }

    @Test
    @Order(2)
    void testGetComments() {
        Comment  comment2 = Comment.builder()
                .projectMember(projectMember)
                .task(task)
                .commentContent("test-comment2")
                .build();

        commentRepository.save(comment);
        commentRepository.save(comment2);

        List<CommentDto> actual = commentRepository.getComments(project.getProjectSeq(), task.getTaskSeq());
        assertThat(actual.get(1).getCommentContent()).isEqualTo(comment2.getCommentContent());

    }

    @Test
    @Order(3)
    void testGetComment() {
        commentRepository.save(comment);

        CommentDto actual = commentRepository.getComment(project.getProjectSeq(),
                                                            task.getTaskSeq(),
                                                            comment.getCommentSeq());

        assertThat(actual.getCommentSeq()).isEqualTo(comment.getCommentSeq());
    }

    @Test
    @Order(4)
    void testGetMyComments(){
        Comment  comment2 = Comment.builder()
                .projectMember(projectMember)
                .task(task)
                .commentContent("test-comment2")
                .build();

        commentRepository.save(comment);
        commentRepository.save(comment2);

        List<CommentDto> actual = commentRepository.getMyComments(projectMember.getProjectMemberId());
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    @Order(5)
    void save() {
        Comment  comment2 = Comment.builder()
                .projectMember(projectMember)
                .task(task)
                .commentContent("test-comment2")
                .build();

        commentRepository.save(comment2);

        Comment actual = commentRepository.findById(comment2.getCommentSeq()).get();
        assertThat(actual.getCommentContent()).isEqualTo(comment2.getCommentContent());
    }

    @Test
    @Order(6)
    void update() {
        commentRepository.save(comment);
        comment.update("test-update");

        Comment actual = commentRepository.findById(comment.getCommentSeq()).get();
        assertThat(actual.getCommentContent()).isEqualTo("test-update");
    }

}