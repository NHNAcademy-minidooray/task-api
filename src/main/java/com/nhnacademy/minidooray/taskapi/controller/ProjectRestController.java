package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.request.project.ProjectModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.project.ProjectRegisterRequest;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectRestController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects() {
        return ResponseEntity.ok(projectService.getProjects());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Integer projectId) {
        return ResponseEntity.ok(projectService.getProject(projectId));
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectRegisterRequest registerRequest,
                                                    BindingResult bindingResult,
                                                    @PathVariable String accountId) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(registerRequest, accountId));
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectDto> modifyProject(@Valid @RequestBody ProjectModifyRequest modifyRequest,
                                                    BindingResult bindingResult,
                                                    @PathVariable Integer projectId) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return ResponseEntity.ok(projectService.modifyProject(modifyRequest, projectId));
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
    }


}
