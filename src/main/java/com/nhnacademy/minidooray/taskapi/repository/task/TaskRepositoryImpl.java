package com.nhnacademy.minidooray.taskapi.repository.task;

import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TaskRepositoryImpl extends QuerydslRepositorySupport implements TaskRepositoryCustom {

    public TaskRepositoryImpl() {
        super(Task.class);
    }

    @Override
    public List<TaskDto> getProjectTasks(Integer projectSeq) {
        QProjectMember projectMember = QProjectMember.projectMember;
        QTask task = QTask.task;
        QTag tag = QTag.tag;
        QTaskTag taskTag = QTaskTag.taskTag;
        QMilestone milestone = QMilestone.milestone;

        return from(task)
                .innerJoin(task.projectMember, projectMember)
                .innerJoin(task.milestone, milestone)
                .innerJoin(taskTag)
                .on(task.taskSeq.eq(taskTag.task.taskSeq))
                .innerJoin(tag)
                .on(taskTag.tag.tagSeq.eq(tag.tagSeq))
                .where(projectMember.project.projectSeq.eq(projectSeq))
                .select(Projections.constructor(TaskDto.class, task.taskSeq, task.taskTitle, task.taskContent,
                        task.taskCreatedAt, projectMember.projectMemberId, projectMember.projectMemberRole,
                        milestone.milestoneName, task.taskTags))
                .fetch();
    }

    @Override
    public TaskDto getTask(Integer projectSeq, Integer taskSeq) {
        QProjectMember projectMember = QProjectMember.projectMember;
        QTask task = QTask.task;
        QTag tag = QTag.tag;
        QTaskTag taskTag = QTaskTag.taskTag;
        QMilestone milestone = QMilestone.milestone;

        return from(task)
                .innerJoin(task.projectMember, projectMember)
                .innerJoin(task.milestone, milestone)
                .innerJoin(taskTag)
                .on(task.taskSeq.eq(taskTag.task.taskSeq))
                .innerJoin(tag)
                .on(taskTag.tag.tagSeq.eq(tag.tagSeq))
                .where(projectMember.project.projectSeq.eq(projectSeq))
                .where(task.taskSeq.eq(taskSeq))
                .select(Projections.constructor(TaskDto.class, task.taskSeq, task.taskTitle, task.taskContent,
                        task.taskCreatedAt, projectMember.projectMemberId, projectMember.projectMemberRole,
                        milestone.milestoneName, task.taskTags))
                .fetch().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("해당 업무는 찾을 수 없습니다."));
    }

    @Override
    public List<TaskDto> getTasks(Integer projectSeq, String projectMemberId) {
        QProjectMember projectMember = QProjectMember.projectMember;
        QTask task = QTask.task;
        QTag tag = QTag.tag;
        QTaskTag taskTag = QTaskTag.taskTag;
        QMilestone milestone = QMilestone.milestone;

        return from(task)
                .innerJoin(task.projectMember, projectMember)
                .innerJoin(task.milestone, milestone)
                .innerJoin(taskTag)
                .on(task.taskSeq.eq(taskTag.task.taskSeq))
                .innerJoin(tag)
                .on(taskTag.tag.tagSeq.eq(tag.tagSeq))
                .where(projectMember.project.projectSeq.eq(projectSeq))
                .where(projectMember.projectMemberId.eq(projectMemberId))
                .select(Projections.constructor(TaskDto.class, task.taskSeq, task.taskTitle, task.taskContent,
                        task.taskCreatedAt, projectMember.projectMemberId, projectMember.projectMemberRole,
                        milestone.milestoneName, task.taskTags))
                .fetch();
    }
}
