package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.request.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.comment.CommentRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.CommentDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.nhnacademy.minidooray.taskapi.exception.ForbiddenException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.comment.CommentRepository;
import com.nhnacademy.minidooray.taskapi.repository.projectmember.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.task.TaskRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectMemberRepository projectMemberRepository;

    CommentRegisterRequest registerRequest;
    CommentModifyRequest modifyRequest;
    @BeforeEach
    void setUp() {
        registerRequest = CommentRegisterRequest.builder()
                .content("test")
                .build();

        modifyRequest = CommentModifyRequest.builder()
                .content("test")
                .build();
    }

    @Test
    @Order(1)
    void getCommentsTaskNotFoundExceptionTest() {
        when(taskRepository.getTask(anyInt(), anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.getComments(1, 1));
    }

    @Test
    @Order(2)
    void getCommentsTest() {
        when(taskRepository.getTask(anyInt(), anyInt()))
                .thenReturn(mock(TaskDto.class));

        List<CommentDto> testList = List.of(mock(CommentDto.class));

        when(commentRepository.getComments(anyInt(), anyInt()))
                .thenReturn(testList);

        List<CommentDto> actual = commentService.getComments(1, 1);
        assertThat(actual.size()).isEqualTo(testList.size());
    }

    @Test
    @Order(3)
    void getCommentTaskNotFoundExceptionTest() {
        when(taskRepository.getTask(anyInt(), anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.getComment(1, 1, 1));
    }

    @Test
    @Order(4)
    void getCommentCommentNotFoundExceptionTest() {
        when(taskRepository.getTask(anyInt(), anyInt()))
                .thenReturn(mock(TaskDto.class));
        when(commentRepository.findByCommentSeqAndTask_TaskSeq(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.getComment(1, 1, 1));
    }

    @Test
    @Order(5)
    void getCommentTest() {
        when(taskRepository.getTask(anyInt(), anyInt()))
                .thenReturn(mock(TaskDto.class));
        when(commentRepository.findByCommentSeqAndTask_TaskSeq(anyInt(), anyInt()))
                .thenReturn(Optional.of(mock(Comment.class)));

        when(commentRepository.getComment(1, 1, 1))
                .thenReturn(mock(CommentDto.class));
        assertThat(commentService.getComment(1, 1, 1))
                .isInstanceOf(CommentDto.class);
    }

    @Test
    @Order(6)
    void getMyCommentMemberNotFoundExceptionTest() {
        when(projectMemberRepository.findByProjectMemberId(anyString()))
                .thenReturn(Collections.emptyList());


        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.getMyComment(anyString()));
    }

    @Test
    @Order(7)
    void getMyCommentTest() {
        when(projectMemberRepository.findByProjectMemberId(anyString()))
                .thenReturn(List.of(mock(ProjectMember.class)));

        List<CommentDto> testList = List.of(mock(CommentDto.class));
        when(commentRepository.getMyComments(anyString()))
                .thenReturn(testList);

        List<CommentDto> actual = commentService.getMyComment(anyString());
        assertThat(actual.size()).isEqualTo(testList.size());
    }

    @Test
    @Order(8)
    void createCommentForbiddenExceptionTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(ForbiddenException.class,
                () -> commentService.createComment(registerRequest, 1, 1, "test"));
    }

    @Test
    @Order(9)
    void createCommentNotFoundExceptionTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository
                .findByTaskSeqAndProjectMember_Project_ProjectSeq(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.createComment(registerRequest, 1, 1, "test"));
    }

//    @Test
    @Order(10)
    void createComment() {
        when(projectMemberRepository.findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository.findByTaskSeqAndProjectMember_Project_ProjectSeq(anyInt(), anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));

        when(commentRepository.getComment(anyInt(), anyInt(), anyInt()))
                .thenReturn(mock(CommentDto.class));

        CommentDto actual = commentService.createComment(registerRequest, 1, 1, "test");
        assertThat(actual)
                .isInstanceOf(CommentDto.class);
    }

    @Test
    @Order(11)
    void updateCommentForbiddenExceptionTest() {
        when(projectMemberRepository.
                findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(ForbiddenException.class,
                () -> commentService.updateComment(modifyRequest, 1, 1, 1, "test"));
    }

    @Test
    @Order(11)
    void updateCommentTaskNotFoundExceptionTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository
                .findByTaskSeqAndProjectMember_Project_ProjectSeq(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.updateComment(modifyRequest, 1, 1, 1, "test"));
    }

    @Test
    @Order(11)
    void updateCommentCommentNotFoundExceptionTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository
                .findByTaskSeqAndProjectMember_Project_ProjectSeq(anyInt(), anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));
        when(commentRepository
                .findByCommentSeqAndTask_TaskSeq(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.updateComment(modifyRequest, 1, 1, 1, "test"));
    }

    @Test
    @Order(11)
    void updateCommentTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository
                .findByTaskSeqAndProjectMember_Project_ProjectSeq(anyInt(), anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));
        when(commentRepository
                .findByCommentSeqAndTask_TaskSeq(anyInt(), anyInt()))
                .thenReturn(Optional.of(mock(Comment.class)));

        when(commentRepository.getComment(anyInt(), anyInt(), anyInt()))
                .thenReturn(mock(CommentDto.class));

        CommentDto actual = commentService.updateComment(modifyRequest, 1, 1, 1, "test");
        assertThat(actual)
                .isEqualTo(commentRepository.getComment(1, 1, 1));
    }

    @Test
    @Order(12)
    void deleteCommentForbiddenExceptionTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(ForbiddenException.class,
                () -> commentService.deleteComment(1, 1, 1, "test"));

    }

    @Test
    @Order(13)
    void deleteCommentTaskNotFoundExceptionTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository
                .findByTaskSeqAndProjectMember_Project_ProjectSeq(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.deleteComment(1, 1, 1, "test"));
    }

    @Test
    @Order(14)
    void deleteCommentCommentNotFoundExceptionTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository
                .findByTaskSeqAndProjectMember_Project_ProjectSeq(anyInt(), anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));
        when(commentRepository
                .findByCommentSeqAndTask_TaskSeq(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> commentService.deleteComment(1, 1, 1, "test"));
    }

    @Test
    @Order(15)
    void deleteCommentTest() {
        when(projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(anyString(), anyInt()))
                .thenReturn(mock(ProjectMember.class));
        when(taskRepository
                .findByTaskSeqAndProjectMember_Project_ProjectSeq(anyInt(), anyInt()))
                .thenReturn(Optional.of(mock(Task.class)));
        when(commentRepository
                .findByCommentSeqAndTask_TaskSeq(anyInt(), anyInt()))
                .thenReturn(Optional.of(mock(Comment.class)));

        commentService.deleteComment(1, 1, 1, "test");
        verify(commentRepository, times(1)).delete(any());
    }
}