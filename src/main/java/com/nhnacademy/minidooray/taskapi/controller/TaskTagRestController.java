package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import com.nhnacademy.minidooray.taskapi.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskTagRestController {
    private final TaskTagService taskTagService;

    @GetMapping("/tasktags/{taskId}")
    public ResponseEntity<List<TaskTagDto>> getTagsByTaskSeq(@PathVariable Integer taskId) {
        List<TaskTagDto> taskTags = new ArrayList<>();
        taskTags = taskTagService.getTaskTagByTaskSeq(taskId);
        return ResponseEntity.ok(taskTags);
    }
}
