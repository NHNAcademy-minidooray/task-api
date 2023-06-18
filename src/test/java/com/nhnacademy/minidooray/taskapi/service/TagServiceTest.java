package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.request.tag.TagModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.tag.TagRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.tag.TagRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagServiceTest {

    @InjectMocks
    private TagService tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ProjectRepository projectRepository;

    TagRegisterRequest registerRequest;
    TagModifyRequest modifyRequest;
    @BeforeEach
    void setUp() {
        registerRequest = TagRegisterRequest.builder()
                .name("test")
                .build();

        modifyRequest = TagModifyRequest.builder()
                .name("test")
                .build();
    }

    @Test
    @Order(1)
    void getTagsExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.getTags(1));
    }

    @Test
    @Order(2)
    void getTagsTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);

        List<TagDto> testList = List.of(mock(TagDto.class));

        when(tagRepository.getTags(anyInt()))
                .thenReturn(testList);

        List<TagDto> actual = tagService.getTags(1);
        assertThat(actual.size()).isEqualTo(testList.size());
    }

    @Test
    @Order(3)
    void getTasksProjectNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.getTasks(1, 1));
    }

    @Test
    @Order(3)
    void getTasksTagNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.getTag(anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.getTasks(1, 1));
    }

    @Test
    @Order(4)
    void getTasksTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.getTag(anyInt()))
                .thenReturn(mock(TagDto.class));

        List<TaskListDto> testList = List.of(mock(TaskListDto.class));
        when(tagRepository.getTasks(anyInt(), anyInt()))
                .thenReturn(testList);

        List<TaskListDto> actual = tagService.getTasks(1, 1);
        assertThat(actual.size())
                .isEqualTo(testList.size());
    }

    @Test
    @Order(5)
    void getTagProjectNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.getTag(1, 1));
    }

    @Test
    @Order(6)
    void getTagTagNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.getTag(anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.getTag(1, 1));
    }

    @Test
    @Order(7)
    void getTagTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.getTag(anyInt()))
                .thenReturn(mock(TagDto.class));

        assertThat(tagService.getTag(1, 1))
                .isInstanceOf(TagDto.class);
    }

    @Test
    @Order(8)
    void createTagProjectNotFoundExceptionTest() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.createTag(registerRequest, 1));
    }

    @Test
    @Order(9)
    void createTag() {
        when(projectRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Project.class)));

        Tag actual = Tag.builder()
                .tagName("test")
                .project(mock(Project.class))
                .build();
        when(tagRepository.save(any(Tag.class)))
                .thenReturn(actual);

        when(tagRepository.getTag(actual.getTagSeq()))
                .thenReturn(mock(TagDto.class));
        assertThat(tagService.createTag(registerRequest, 1))
                .isInstanceOf(TagDto.class);
    }

    @Test
    @Order(10)
    void updateTagProjectNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.updateTag(modifyRequest, 1, 1));
    }

    @Test
    @Order(11)
    void updateTagTagNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.updateTag(modifyRequest, 1, 1));
    }

    @Test
    @Order(12)
    void updateTagNotThisProjectTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Tag.class)));
        when(tagRepository.getTag(anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.updateTag(modifyRequest, 1, 1));
    }

    @Test
    @Order(13)
    void updateTagTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Tag.class)));
        when(tagRepository.getTag(anyInt()))
                .thenReturn(mock(TagDto.class));

        when(tagRepository.getTag(anyInt()))
                .thenReturn(mock(TagDto.class));

        TagDto actual = tagService.updateTag(modifyRequest, 1, 1);
        assertThat(actual)
                .isEqualTo(tagRepository.getTag(1));
    }

    @Test
    @Order(14)
    void deleteTagProjectNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(false);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.deleteTag(1, 1));
    }

    @Test
    @Order(15)
    void deleteTagTagNotFoundExceptionTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.deleteTag(1, 1));
    }

    @Test
    @Order(16)
    void deleteTagNotThisProjectTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Tag.class)));
        when(tagRepository.getTag(anyInt()))
                .thenReturn(null);

        Assertions.assertThrows(NotFoundException.class,
                () -> tagService.deleteTag(1, 1));
    }

    @Test
    @Order(17)
    void deleteTagTest() {
        when(projectRepository.existsById(anyInt()))
                .thenReturn(true);
        when(tagRepository.findById(anyInt()))
                .thenReturn(Optional.of(mock(Tag.class)));
        when(tagRepository.getTag(anyInt()))
                .thenReturn(mock(TagDto.class));

        tagService.deleteTag(1, 1);
        verify(tagRepository, times(1)).delete(any(Tag.class));
    }
}