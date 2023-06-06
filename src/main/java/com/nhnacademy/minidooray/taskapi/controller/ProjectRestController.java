package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.domain.request.ProjectModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.ProjectRegisterRequest;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.service.ProjectService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectRegisterRequest registerRequest, @PathVariable String accountId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(registerRequest, accountId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDto> modifyProject(@RequestBody ProjectModifyRequest modifyRequest, @PathVariable Integer id) {
        return ResponseEntity.ok(projectService.modifyProject(modifyRequest, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
    }


}
