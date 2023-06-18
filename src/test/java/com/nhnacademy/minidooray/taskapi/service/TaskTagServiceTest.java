package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.repository.tasktag.TaskTagRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskTagServiceTest {

    @InjectMocks
    private TaskTagService taskTagService;
    @Mock
    private TaskTagRepository taskTagRepository;

    @Test
    void getTaskTagByTaskSeqTest() {
        List<TaskTagDto> testList = List.of(mock(TaskTagDto.class));
        when(taskTagRepository.getTagsByTaskSeq(anyInt()))
                .thenReturn(testList);

        List<TaskTagDto> actual = taskTagService.getTaskTagByTaskSeq(1);
        assertThat(actual.size()).isEqualTo(testList.size());
    }
}