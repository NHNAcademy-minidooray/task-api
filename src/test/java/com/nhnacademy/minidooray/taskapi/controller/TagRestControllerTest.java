package com.nhnacademy.minidooray.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.minidooray.taskapi.domain.request.tag.TagModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.tag.TagRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.TagService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagRestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TagService tagService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    void getTags() throws Exception {
        TagDto tagDto1 = TagDto.builder()
                .projectSeq(1)
                .tagSeq(1)
                .tagName("tag1")
                .build();

        TagDto tagDto2 = TagDto.builder()
                .projectSeq(1)
                .tagSeq(2)
                .tagName("tag2")
                .build();

        List<TagDto> tags = List.of(tagDto1, tagDto2);

        when(tagService.getTags(any())).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tags", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].project-id").value(1))
                .andExpect(jsonPath("$[0].id").value(tagDto1.getTagSeq()))
                .andExpect(jsonPath("$[1].project-id").value(1))
                .andExpect(jsonPath("$[1].id").value(tagDto2.getTagSeq()));

    }

    @Test
    @Order(2)
    void getTasks() throws Exception {
        TaskListDto taskListDto1 = TaskListDto.builder()
                .taskSeq(1)
                .taskTitle("task-title-1")
                .projectSeq(1)
                .projectTitle("project-1")
                .build();

        TaskListDto taskListDto2 = TaskListDto.builder()
                .taskSeq(2)
                .taskTitle("task-title-2")
                .projectSeq(2)
                .projectTitle("project-2")
                .build();

        List<TaskListDto> tasks = List.of(taskListDto1, taskListDto2);

        when(tagService.getTasks(any(), any())).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}/tasks/tags/{tagId}", 1, 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskListDto1.getTaskSeq()))
                .andExpect(jsonPath("$[1].id").value(taskListDto2.getTaskSeq()));
    }

    @Test
    @Order(3)
    void createTag() throws Exception {
        TagDto tagDto = TagDto.builder()
                .projectSeq(1)
                .tagSeq(1)
                .tagName("tag-name")
                .build();


        TagRegisterRequest request = TagRegisterRequest.builder()
                .name("tag-name")
                .build();

        when(tagService.createTag(any(), any())).thenReturn(tagDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/tags", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(tagDto.getTagName()));
    }

    @Test
    @Order(4)
    void createTagNullTest() {
        TagRegisterRequest request = TagRegisterRequest.builder()
                .name(null)
                .build();

        when(tagService.createTag(request, 1)).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> tagService.createTag(request, 1));
    }

    @Test
    @Order(5)
    void updateTag() throws Exception {
        TagDto tagDto = TagDto.builder()
                .projectSeq(1)
                .tagSeq(1)
                .tagName("tag-name-update")
                .build();


        TagModifyRequest request = TagModifyRequest.builder()
                .name("tag-name-update")
                .build();

        when(tagService.updateTag(any(), any(), any())).thenReturn(tagDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/projects/{projectId}/tags/{tagId}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("tag-name-update"));
    }

    @Test
    @Order(6)
    void updateTagNullTest() {
        TagModifyRequest request = TagModifyRequest.builder()
                .name("")
                .build();

        when(tagService.updateTag(request, 1, 1)).thenThrow(ValidationFailedException.class);

        Assertions.assertThrows(ValidationFailedException.class,
                () -> tagService.updateTag(request, 1, 1));
    }

    @Test
    @Order(7)
    void deleteTag() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/projects/{projectId}/tags/{tagId}", 1, 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}