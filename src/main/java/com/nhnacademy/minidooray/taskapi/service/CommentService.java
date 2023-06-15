package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.request.comment.CommentModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.comment.CommentRegisterRequest;
import com.nhnacademy.minidooray.taskapi.domain.response.CommentDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.entity.Comment;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.exception.ForbiddenException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.comment.CommentRepository;
import com.nhnacademy.minidooray.taskapi.repository.projectmember.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public List<CommentDto> getComments(Integer projectSeq, Integer taskSeq) {
        if (Objects.isNull(taskRepository.getTask(projectSeq, taskSeq))) {
            throw new NotFoundException("해당 업무를 찾을 수 없습니다.");
        }
        return commentRepository.getComments(projectSeq, taskSeq);
    }

    public CommentDto getComment(Integer projectSeq, Integer taskSeq, Integer commentSeq) {
        if (Objects.isNull(taskRepository.getTask(projectSeq, taskSeq))) {
            throw new NotFoundException("해당 업무를 찾을 수 없습니다.");
        }

        commentRepository.findByCommentSeqAndTask_TaskSeq(commentSeq, taskSeq).orElseThrow(
                () -> new NotFoundException("해당 댓글을 찾을 수 없습니다."));

        return commentRepository.getComment(projectSeq, taskSeq, commentSeq);
    }

    public List<CommentDto> getMyComment(String projectMemberId) {
        List<ProjectMember> projectMember = projectMemberRepository.findByProjectMemberId(projectMemberId);
        if(projectMember.size() == 0) {
            throw new NotFoundException("등록되지 않은 멤버입니다.");
        }

        return commentRepository.getMyComments(projectMemberId);
    }

    @Transactional
    public CommentDto createComment(CommentRegisterRequest request, Integer projectSeq, Integer taskSeq, String projectMemberId) {
        ProjectMember projectMember = projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(projectMemberId, projectSeq);

        if(Objects.isNull(projectMember)) {
            throw new ForbiddenException("댓글 등록 권한이 없습니다.");
        }

        Task task = taskRepository.findByTaskSeqAndProjectMember_Project_ProjectSeq(taskSeq, projectSeq)
                .orElseThrow(() -> new NotFoundException("해당 업무를 찾지 못했습니다."));

        Comment comment = Comment.builder()
                .commentContent(request.getContent())
                .projectMember(projectMember)
                .task(task)
                .build();

        commentRepository.save(comment);

        return commentRepository.getComment(projectSeq, taskSeq, comment.getCommentSeq());
    }

    @Transactional
    public CommentDto updateComment(CommentModifyRequest request, Integer projectSeq, Integer taskSeq, Integer commentSeq,
                                    String projectMemberId) {
        ProjectMember projectMember = projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(projectMemberId, projectSeq);

        if(Objects.isNull(projectMember)) {
            throw new ForbiddenException("댓글 수정 권한이 없습니다.");
        }

        Task task = taskRepository.findByTaskSeqAndProjectMember_Project_ProjectSeq(taskSeq, projectSeq)
                .orElseThrow(() -> new NotFoundException("해당 업무를 찾지 못했습니다."));

        Comment comment = commentRepository.findByCommentSeqAndTask_TaskSeq(commentSeq, taskSeq)
                .orElseThrow(() -> new NotFoundException("해당 댓글을 찾지 못했습니다."));

        comment.update(request.getContent());

        return commentRepository.getComment(projectSeq, taskSeq, comment.getCommentSeq());
    }

    @Transactional
    public void deleteComment(Integer projectSeq, Integer taskSeq, Integer commentSeq, String projectMemberId) {
        ProjectMember projectMember = projectMemberRepository
                .findByProjectMemberIdAndProject_ProjectSeq(projectMemberId, projectSeq);

        if(Objects.isNull(projectMember)) {
            throw new ForbiddenException("댓글 삭제 권한이 없습니다.");
        }

        Task task = taskRepository.findByTaskSeqAndProjectMember_Project_ProjectSeq(taskSeq, projectSeq)
                .orElseThrow(() -> new NotFoundException("해당 업무를 찾지 못했습니다."));

        Comment comment = commentRepository.findByCommentSeqAndTask_TaskSeq(commentSeq, taskSeq)
                .orElseThrow(() -> new NotFoundException("해당 댓글을 찾지 못했습니다."));

        commentRepository.delete(comment);
    }
}
