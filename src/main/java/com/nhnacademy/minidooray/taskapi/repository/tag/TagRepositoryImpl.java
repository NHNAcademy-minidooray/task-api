package com.nhnacademy.minidooray.taskapi.repository.tag;

import com.nhnacademy.minidooray.taskapi.domain.response.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

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
    public List<TaskListDto> getTasks(Integer projectSeq, Integer tagSeq) {
        QTag tag = QTag.tag;
        QTaskTag taskTag = QTaskTag.taskTag;
        QTask task = QTask.task;
        QProjectMember projectMember = QProjectMember.projectMember;

        return from(tag)
                .innerJoin(taskTag)
                .on(tag.tagSeq.eq(taskTag.pk.tagSeq))
                .innerJoin(task)
                .on(taskTag.task.taskSeq.eq(task.taskSeq))
                .innerJoin(projectMember)
                .on(task.projectMember.projectMemberSeq.eq(projectMember.projectMemberSeq))
                .where(projectMember.project.projectSeq.eq(projectSeq))
                .where(tag.tagSeq.eq(tagSeq))
                .distinct()
                .select(Projections.constructor(TaskListDto.class, task.taskSeq, task.taskTitle))
                .fetch();
    }

    @Override
    public TagDto getTag(Integer tagSeq) {
        QTag tag = QTag.tag;
        QProject project = QProject.project;
        return from(tag)
                .innerJoin(tag.project, project)
                .where(tag.tagSeq.eq(tagSeq))
                .select(Projections.constructor(TagDto.class, tag.tagSeq, tag.tagName, project.projectSeq))
                .fetchOne();
    }

}
