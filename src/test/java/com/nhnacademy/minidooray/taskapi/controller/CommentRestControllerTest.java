package com.nhnacademy.minidooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.request.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.comment.CommentRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.CommentDto;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.CommentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(CommentRestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    void getComments() throws Exception {
        CommentDto commentDto1 = CommentDto.builder()
                .commentContent("comment")
                .projectMemberId("test")
                .commentSeq(1)
                .build();

        CommentDto commentDto2 = CommentDto.builder()
                .commentContent("comment2")
                .projectMemberId("test2")
                .commentSeq(1)
                .build();

        List<CommentDto> comments = List.of(commentDto1, commentDto2);

        when(commentService.getComments(any(), any())).thenReturn(comments);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks/{taskId}/comments", 1, 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(commentDto1.getCommentContent()))
                .andExpect(jsonPath("$[1].content").value(commentDto2.getCommentContent()));
    }

    @Test
    @Order(2)
    void getComment() throws Exception {
        CommentDto commentDto = CommentDto.builder()
                .commentContent("comment")
                .projectMemberId("test")
                .commentSeq(1)
                .build();

        when(commentService.getComment(any(), any(), any())).thenReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks/{taskId}/comments/{commentId}", 1, 1, 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentDto.getCommentSeq()))
                .andExpect(jsonPath("$.writer").value(commentDto.getProjectMemberId()));
    }

    @Test
    @Order(3)
    void getMyComments() throws Exception {
        CommentDto commentDto1 = CommentDto.builder()
                .commentContent("comment")
                .projectMemberId("test")
                .commentSeq(1)
                .build();

        CommentDto commentDto2 = CommentDto.builder()
                .commentContent("comment2")
                .projectMemberId("test")
                .commentSeq(1)
                .build();

        List<CommentDto> comments = List.of(commentDto1, commentDto2);

        when(commentService.getMyComment(anyString())).thenReturn(comments);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/comments/{accountId}", "test")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].writer").value("test"))
                .andExpect(jsonPath("$[1].writer").value("test"));
    }

    @Test
    @Order(4)
    void createComment() throws Exception {
        CommentDto commentDto = CommentDto.builder()
                .commentContent("comment")
                .projectMemberId("test")
                .commentSeq(1)
                .build();

        CommentRegisterRequest request = CommentRegisterRequest.builder()
                .content("comment")
                .build();

        when(commentService.createComment(any(), any(), any(), any())).thenReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/projects/{projectId}/tasks/{taskId}/comments/accounts/{accountId}", 1, 1, "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(commentDto.getCommentSeq()));

    }

    @Test
    @Order(5)
    void createCommentNullTest() {

        CommentRegisterRequest request = CommentRegisterRequest.builder()
                .content("")
                .build();

        when(commentService.createComment(request, 1, 1, "test")).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> commentService.createComment(request, 1, 1, "test"));

    }

    @Test
    @Order(6)
    void updateComment() throws Exception {
        CommentDto commentDto = CommentDto.builder()
                .commentContent("comment-update")
                .projectMemberId("test")
                .commentSeq(1)
                .build();

        CommentModifyRequest request = CommentModifyRequest.builder()
                .content("comment-update")
                .build();

        when(commentService.updateComment(any(), any(), any(), any(), any())).thenReturn(commentDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/projects/{projectId}/tasks/{taskId}/comments/{commentId}/accounts/{accountId}", 1, 1, 1, "test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentDto.getCommentSeq()));
    }

    @Test
    @Order(7)
    void updateCommentNullTest() {
        CommentModifyRequest request = CommentModifyRequest.builder()
                .content(null)
                .build();

        when(commentService.updateComment(request, 1, 1, 1, "test")).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> commentService.updateComment(request, 1, 1, 1, "test"));

    }

    @Test
    @Order(8)
    void deleteComment() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/projects/{projectId}/tasks/{taskId}/comments/{commentId}/accounts/{accountId}", 1, 1, 1, "test")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}