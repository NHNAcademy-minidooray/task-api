package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.domain.request.projectmember.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectMemberRestController {
    private final ProjectMemberService projectMemberService;

    @GetMapping("/projects/{projectId}/accounts")
    public ResponseEntity<List<ProjectMemberDto>> getProjectMembers(@PathVariable Integer projectId) {
        return ResponseEntity.ok(projectMemberService.getProjectMembers(projectId));
    }

    @GetMapping("/projects/{projectId}/accounts/{accountId}")
    public ResponseEntity<ProjectMemberDto> getProjectMember(@PathVariable Integer projectId,
                                                             @PathVariable String accountId) {
        return ResponseEntity.ok(projectMemberService.getProjectMember(projectId, accountId));
    }

    /**
     * 해당 계정의 프로젝트 조회
     * @param accountId
     * @return
     */
    @GetMapping("/projects/accounts/{accountId}")
    public ResponseEntity<List<ProjectDto>> getProjects(@PathVariable String accountId) {
        return ResponseEntity.ok(projectMemberService.getProjects(accountId));
    }

    @PostMapping("/projects/{projectId}/accounts/{accountId}")
    public ResponseEntity<ProjectMemberDto> createProjectMember(@Valid @RequestBody ProjectMemberRegisterRequest registerRequest,
                                                                BindingResult bindingResult,
                                                                @PathVariable Integer projectId,
                                                                @PathVariable String accountId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectMemberService.createProjectMember(registerRequest, projectId, accountId));
    }

    @DeleteMapping("/projects/{projectId}/accounts/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjectMember(@PathVariable("projectId")Integer projectSeq,
                                                    @PathVariable String accountId) {
        projectMemberService.deleteProjectMember(projectSeq, accountId);
    }
}
