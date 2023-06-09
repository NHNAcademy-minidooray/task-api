package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.request.task.TaskModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.task.TaskRegisterRequest;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.nhnacademy.minidooray.taskapi.exception.*;
import com.nhnacademy.minidooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.tag.TagRepository;
import com.nhnacademy.minidooray.taskapi.repository.tasktag.TaskTagRepository;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.projectmember.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final MilestoneRepository milestoneRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;

    public List<TaskDto> getProjectTasks(Integer projectSeq) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로젝트입니다."));

        return taskRepository.getProjectTasks(projectSeq);
    }

    public TaskDto getTask(Integer projectSeq, Integer taskSeq) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로젝트입니다."));

        taskRepository.findById(taskSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 업무입니다."));

        return taskRepository.getTask(projectSeq, taskSeq);
    }

    public List<TaskDto> getTasks(Integer projectSeq, String projectMemberId) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로젝트입니다."));

        ProjectMember projectMember = projectMemberRepository.findByProjectMemberId(projectMemberId).orElseThrow(
                () -> new NotFoundException("등록되지 않은 프로젝트 멤버입니다."));

        return taskRepository.getTasks(projectSeq, projectMemberId);
    }


    @Transactional
    public TaskDto createTask(TaskRegisterRequest registerRequest, Integer projectSeq, String projectMemberId) {
        if(!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트입니다.");
        }

        ProjectMember projectMember = projectMemberRepository.findByProjectMemberId(projectMemberId)
                .orElseThrow(() -> new NotFoundException("등록되지 않은 프로젝트 멤버입니다."));

        Milestone milestone = milestoneRepository.findByMilestoneName(registerRequest.getMilestoneName())
                .orElseThrow(() -> new NotFoundException("등록되지 않은 마일스톤입니다."));

        List<Tag> tags = new ArrayList<>();
        if(registerRequest.getTagNames().size() > 0) {
            for(String tagName : registerRequest.getTagNames()) {
                if(!tagRepository.existsByTagName(tagName)) {
                    throw new NotFoundException("등록되지 않은 태그입니다.");
                }else {
                    tags.add(tagRepository.findByTagName(tagName).get());
                }
            }
        }

        // 업무 등록
        Task task = Task.builder()
                .taskTitle(registerRequest.getTitle())
                .taskContent(registerRequest.getContent())
                .taskCreatedAt(LocalDateTime.now())
                .milestone(milestone)
                .projectMember(projectMember)
                .build();

        taskRepository.save(task);

        //TaskTag 테이블 값 등록
        if(tags.size() > 0) {
            for(Tag tag : tags) {
                taskTagRepository.save(TaskTag.builder()
                        .pk(TaskTag.Pk.builder()
                                .taskSeq(task.getTaskSeq())
                                .tagSeq(tag.getTagSeq())
                                .build())
                        .tag(tag)
                        .task(task)
                        .build());
            }
        }

        return taskRepository.getTask(projectMember.getProject().getProjectSeq(), task.getTaskSeq());
    }


    @Transactional
    public TaskDto modifyTask(TaskModifyRequest modifyRequest, Integer projectSeq, Integer taskSeq, String projectMemberId) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로젝트입니다."));

        Task task = taskRepository.findById(taskSeq).orElseThrow(
                    () -> new NotFoundException("존재하지 않는 업무입니다."));

        ProjectMember projectMember = taskRepository.findByTaskSeq(taskSeq).orElseThrow(
                () -> new NotFoundException("잘못된 접근입니다."));

        if(!projectMember.getProjectMemberId().equals(projectMemberId)) {
            throw new ForbiddenException("수정 접근 권한이 없습니다.");
        }


        Milestone milestone = milestoneRepository.findByMilestoneName(modifyRequest.getMilestoneName())
                .orElseThrow(() -> new NotFoundException("등록되지 않은 마일스톤입니다."));


        List<Tag> tags = new ArrayList<>();
        List<TaskTag> taskTags = new ArrayList<>();

        //수정 요청 -> tag entity 로 변경
        if(modifyRequest.getTagNames().size() > 0) {
            for(String tagName : modifyRequest.getTagNames()) {
                if(!tagRepository.existsByTagName(tagName)) {
                    throw new NotFoundException("등록되지 않은 태그입니다.");
                }else {
                    tags.add(tagRepository.findByTagName(tagName).get());
                }
            }
        }


        // 기존 업무에 등록된 태그 삭제후 요청한 태그들로 업데이트 진행
        List<TaskTag> deleteTaskTags = taskTagRepository.findByTask_TaskSeq(taskSeq);
        taskTagRepository.deleteAll(deleteTaskTags);


        for(Tag tag : tags) {
            TaskTag taskTag = TaskTag.builder()
                            .pk(TaskTag.Pk.builder()
                                    .tagSeq(tag.getTagSeq())
                                    .taskSeq(taskSeq)
                                    .build())
                            .task(task)
                            .tag(tag)
                            .build();

            taskTags.add(taskTag);
        }

        //변경감지
        task.update(modifyRequest.getTitle(), modifyRequest.getContent(),milestone, taskTags);

        return  taskRepository.getTask(projectSeq, taskSeq);
    }


    @Transactional
    public void deleteTask(Integer projectSeq, Integer taskSeq, String projectMemberId) {

        if(!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("존재하지 않는 프로젝트입니다.");
        }

        Task task = taskRepository.findById(taskSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 업무입니다."));

        ProjectMember projectMember = taskRepository.findByTaskSeq(taskSeq).orElseThrow(
                () -> new NotFoundException("잘못된 접근입니다."));

        if(!projectMember.getProjectMemberId().equals(projectMemberId)) {
            throw new ForbiddenException("수정 접근 권한이 없습니다.");
        }

        taskRepository.delete(task);
    }


}
