package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import com.nhnacademy.minidooray.taskapi.repository.tasktag.TaskTagRepository;
import com.nhnacademy.minidooray.taskapi.repository.tasktag.TaskTagRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskTagService {
    private final TaskTagRepository taskTagRepository;

    public List<TaskTagDto> getTaskTagByTaskSeq(Integer taskSeq) {
        List<TaskTagDto> taskTags = new ArrayList<>();
        taskTags = taskTagRepository.getTagsByTaskSeq(taskSeq);
        return taskTags;
    }
}
