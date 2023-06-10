package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskListDto;
import com.nhnacademy.minidooray.taskapi.domain.request.milestone.MilestoneModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.milestone.MilestoneRegisterRequest;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MilestoneRestController {
    private final MilestoneService milestoneService;

    @GetMapping("/projects/{projectId}/milestones")
    public ResponseEntity<List<MilestoneDto>> getMilestones(@PathVariable Integer projectId) {
        return ResponseEntity.ok(milestoneService.getMilestones(projectId));
    }

    @GetMapping("/projects/{projectId}/tasks/milestones/{milestoneId}")
    public ResponseEntity<List<TaskListDto>> getTasks(@PathVariable Integer projectId, @PathVariable Integer milestoneId) {
        return ResponseEntity.ok(milestoneService.getTasks(projectId, milestoneId));
    }

    @GetMapping("/projects/{projectId}/milestones/{milestoneId}")
    public ResponseEntity<MilestoneDto> getMilestone(@PathVariable Integer projectId, @PathVariable Integer milestoneId) {
        return ResponseEntity.ok(milestoneService.getMilestone(projectId, milestoneId));
    }

    @PostMapping("/projects/{projectId}/milestones")
    public ResponseEntity<MilestoneDto> createMilestone(@Valid  @RequestBody MilestoneRegisterRequest request,
                                                       BindingResult bindingResult,
                                                       @PathVariable Integer projectId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(milestoneService.createMilestone(request, projectId));
    }

    @PatchMapping("/projects/{projectId}/milestones/{milestoneId}")
    public ResponseEntity<MilestoneDto> modifyMilestone(@Valid @RequestBody MilestoneModifyRequest request,
                                                        BindingResult bindingResult,
                                                        @PathVariable Integer projectId,
                                                        @PathVariable Integer milestoneId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return ResponseEntity.ok(milestoneService.modifyMilestone(request, projectId, milestoneId));
    }

    @DeleteMapping("/projects/{projectId}/milestones/{milestoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilestone(@PathVariable Integer projectId, @PathVariable Integer milestoneId) {
        milestoneService.deleteMilestone(projectId, milestoneId);
    }
}
