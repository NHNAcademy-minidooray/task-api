package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.domain.request.task.TaskModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.task.TaskRegisterRequest;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskRestController {
    private final TaskService taskService;

    /**
     * 해당 프로젝트의 전체 업무 조회
     * @param projectId
     * @return 해당 프로젝트 업무 리스트
     */
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskListDto>> getProjectTasks(@PathVariable Integer projectId) {
        return ResponseEntity.ok(taskService.getProjectTasks(projectId));
    }


    /**
     * 해당 업무 조회
     * @param projectId
     * @param taskId
     * @return 해당 업무
     */
    @GetMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Integer projectId, @PathVariable Integer taskId) {
        return ResponseEntity.ok(taskService.getTask(projectId, taskId));
    }

    /**
     * 해당 작성자가 작성한 업무 리스트 조회
     * @param projectId
     * @param accountId
     * @return 작성자가 작성한 업무 리스트
     */
    @GetMapping("/projects/{projectId}/tasks/accounts/{accountId}")
    public ResponseEntity<List<TaskListDto>> getTasks(@PathVariable Integer projectId, @PathVariable String accountId) {
        return ResponseEntity.ok(taskService.getTasks(projectId, accountId));
    }


    /**
     * 업무 등록
     * @param registerRequest
     * @param bindingResult
     * @param projectId
     * @param accountId
     * @return 등록된 업무
     */
    @PostMapping("/projects/{projectId}/tasks/accounts/{accountId}")
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskRegisterRequest registerRequest,
                                              BindingResult bindingResult,
                                              @PathVariable Integer projectId, @PathVariable String accountId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(registerRequest, projectId, accountId));
    }


    /**
     * 해당 업무 수정
     * @param modifyRequest
     * @param bindingResult
     * @param projectId
     * @param taskId
     * @param accountId
     * @return 수정된 업무
     */
    @PatchMapping("/projects/{projectId}/tasks/{taskId}/accounts/{accountId}")
    public ResponseEntity<TaskDto> modifyTask(@Valid @RequestBody TaskModifyRequest modifyRequest,
                                              BindingResult bindingResult, @PathVariable Integer projectId,
                                              @PathVariable Integer taskId, @PathVariable String accountId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return ResponseEntity.ok(taskService.modifyTask(modifyRequest, projectId, taskId, accountId));
    }


    @DeleteMapping("/projects/{projectId}/tasks/{taskId}/accounts/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer projectId, @PathVariable Integer taskId,
                           @PathVariable String accountId) {

        taskService.deleteTask(projectId, taskId, accountId);
    }

}
