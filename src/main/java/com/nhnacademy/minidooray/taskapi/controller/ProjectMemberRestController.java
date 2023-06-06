package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.domain.request.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectMemberRestController {
    private final ProjectMemberService projectMemberService;

    @GetMapping("/projects/{projectId}/accounts")
    public ResponseEntity<List<ProjectMemberDto>> getProjectMembers(@PathVariable("projectId") Integer projectSeq) {
        return ResponseEntity.ok(projectMemberService.getProjectMembers(projectSeq));
    }

    @GetMapping("/projects/{projectId}/accounts/{accountId}")
    public ResponseEntity<ProjectMemberDto> getProjectMember(@PathVariable("projectId")Integer projectSeq,
                                                             @PathVariable String accountId) {
        return ResponseEntity.ok(projectMemberService.getProjectMember(projectSeq, accountId));
    }

    @GetMapping("/projects/accounts/{accountId}")
    public ResponseEntity<List<ProjectDto>> getProjects(@PathVariable String accountId) {
        return ResponseEntity.ok(projectMemberService.getProjects(accountId));
    }

    @PostMapping("/projects/{projectId}/accounts")
    public ResponseEntity<ProjectMemberDto> createProjectMember(@RequestBody ProjectMemberRegisterRequest registerRequest,
                                                                @PathVariable("projectId") Integer projectSeq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectMemberService.createProjectMember(registerRequest, projectSeq));
    }

    @DeleteMapping("/projects/{projectId}/accounts/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjectMember(@PathVariable("projectId")Integer projectSeq,
                                                    @PathVariable String accountId) {
        projectMemberService.deleteProjectMember(projectSeq, accountId);
    }
}
