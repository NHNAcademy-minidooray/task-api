package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.request.tag.TagModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.tag.TagRegisterRequest;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.TagService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagRestController {
    private final TagService tagService;

    @GetMapping("projects/{projectId}/tags")
    public ResponseEntity<List<TagDto>> getTags(@PathVariable Integer projectId) {
        return ResponseEntity.ok(tagService.getTags(projectId));
    }

    @GetMapping("/projects/{projectId}/tasks/tags/{tagId}")
    public ResponseEntity<List<TaskDto>> getTasks(@PathVariable Integer projectId, @PathVariable Integer tagId) {
        return ResponseEntity.ok(tagService.getTasks(projectId, tagId));
    }

    @PostMapping("/projects/{projectId}/tags")
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody TagRegisterRequest registerRequest,
                            BindingResult bindingResult,
                            @PathVariable Integer projectId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(registerRequest, projectId));
    }

    @PatchMapping("/projects/{projectId}/tags/{tagId}")
    public ResponseEntity<TagDto> updateTag(@Valid @RequestBody TagModifyRequest modifyRequest,
                                            BindingResult bindingResult,
                                            @PathVariable Integer projectId, @PathVariable Integer tagId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return ResponseEntity.ok(tagService.updateTag(modifyRequest, projectId, tagId));
    }

    @DeleteMapping("projects/{projectId}/tags/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Integer projectId, @PathVariable Integer tagId) {
        tagService.deleteTag(projectId, tagId);
    }
}
