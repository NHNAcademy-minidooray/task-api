package com.nhnacademy.minidooray.taskapi.repository.tag;

import com.nhnacademy.minidooray.taskapi.domain.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagRepositoryCustom {
    public TagRepositoryImpl() {
        super(Tag.class);
    }

    @Override
    public List<TagDto> getTags(Integer projectSeq) {
        QProject project = QProject.project;
        QTag tag = QTag.tag;

        return from(tag)
                .innerJoin(tag.project, project)
                .where(project.projectSeq.eq(projectSeq))
                .select(Projections.constructor(TagDto.class, tag.tagSeq, tag.tagName, project.projectSeq))
                .fetch();
    }

    @Override
    public List<TaskDto> getTasks(Integer projectSeq, Integer tagSeq) {
        QProject project = QProject.project;
        QTag tag = QTag.tag;
        QTaskTag taskTag = QTaskTag.taskTag;
        QTask task = QTask.task;
        QProjectMember projectMember = QProjectMember.projectMember;
        QMilestone milestone = QMilestone.milestone;

        return from(tag)
                .innerJoin(tag.project, project)
                .innerJoin(taskTag)
                .on(tag.tagSeq.eq(taskTag.pk.tagSeq))
                .innerJoin(task)
                .on(taskTag.task.taskSeq.eq(task.taskSeq))
                .innerJoin(projectMember)
                .on(project.projectSeq.eq(projectMember.project.projectSeq))
                .innerJoin(milestone)
                .on(task.milestone.milestoneSeq.eq(milestone.milestoneSeq))
                .where(project.projectSeq.eq(projectSeq))
                .where(tag.tagSeq.eq(tagSeq))
                .distinct()
                .select(Projections.constructor(TaskDto.class, task.taskSeq, task.taskTitle, task.taskContent,
                        task.taskCreatedAt, task.projectMember.projectMemberId, task.projectMember.projectMemberRole,
                        milestone.milestoneName, Projections.list(taskTag)))
                .fetch();
    }

    @Override
    public Optional<TagDto> getTag(Integer tagSeq) {
        QTag tag = QTag.tag;
        QProject project = QProject.project;
        return from(tag)
                .innerJoin(tag.project, project)
                .where(tag.tagSeq.eq(tagSeq))
                .select(Projections.constructor(TagDto.class, tag.tagSeq, tag.tagName, project.projectSeq))
                .stream().findFirst();
    }

}
