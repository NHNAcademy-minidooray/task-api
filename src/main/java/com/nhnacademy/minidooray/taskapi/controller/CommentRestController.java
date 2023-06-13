package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.request.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.comment.CommentRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.CommentDto;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooray.taskapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    @GetMapping("/projects/{projectId}/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Integer projectId, @PathVariable Integer taskId) {
        return ResponseEntity.ok(commentService.getComments(projectId, taskId));
    }

    @GetMapping("/projects/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Integer projectId, @PathVariable Integer taskId,
                                                 @PathVariable Integer commentId) {

        return ResponseEntity.ok(commentService.getComment(projectId, taskId, commentId));
    }

    @GetMapping("/comments/{accountId}")
    public ResponseEntity<List<CommentDto>> getMyComments(@PathVariable String accountId) {
        return ResponseEntity.ok(commentService.getMyComment(accountId));
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/comments/accounts/{accountId}")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentRegisterRequest request,
                                                    BindingResult bindingResult,
                                                    @PathVariable Integer projectId,
                                                    @PathVariable Integer taskId,
                                                    @PathVariable String accountId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(request, projectId, taskId, accountId));
    }

    @PatchMapping("/projects/{projectId}/tasks/{taskId}/comments/{commentId}/accounts/{accountId}")
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentModifyRequest request,
                                                    BindingResult bindingResult,
                                                    @PathVariable Integer projectId,
                                                    @PathVariable Integer taskId,
                                                    @PathVariable Integer commentId,
                                                    @PathVariable String accountId) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return ResponseEntity.ok(commentService.updateComment(request, projectId, taskId, commentId, accountId));
    }

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}/comments/{commentId}/accounts/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Integer projectId, @PathVariable Integer taskId,
                              @PathVariable Integer commentId, @PathVariable String accountId) {
        commentService.deleteComment(projectId, taskId, commentId, accountId);
    }
}
