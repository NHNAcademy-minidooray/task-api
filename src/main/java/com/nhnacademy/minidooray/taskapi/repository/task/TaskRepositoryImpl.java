package com.nhnacademy.minidooray.taskapi.repository.task;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.querydsl.core.types.Projections.list;

public class TaskRepositoryImpl extends QuerydslRepositorySupport implements TaskRepositoryCustom {

    public TaskRepositoryImpl() {
        super(Task.class);
    }

    /**
     * 해당 프로젝트의 업무 리스트
     *
     * @param projectSeq
     * @return
     */
    @Override
    public List<TaskListDto> getProjectTasks(Integer projectSeq) {
        QProjectMember projectMember = QProjectMember.projectMember;
        QTask task = QTask.task;


        return from(task)
                .innerJoin(task.projectMember, projectMember)
                .where(projectMember.project.projectSeq.eq(projectSeq))
                .orderBy(task.taskSeq.desc())
                .select(Projections.constructor(TaskListDto.class, task.taskSeq, task.taskTitle))
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
                .leftJoin(task.milestone, milestone)
                .leftJoin(taskTag)
                .on(task.taskSeq.eq(taskTag.task.taskSeq))
                .leftJoin(tag)
                .on(taskTag.tag.tagSeq.eq(tag.tagSeq))
                .where(task.projectMember.project.projectSeq.eq(projectSeq)
                        .and(task.taskSeq.eq(taskSeq)))
                .distinct()
                .transform(
                        GroupBy.groupBy(task.taskSeq).list(
                                Projections.constructor(TaskDto.class,
                                        task.taskSeq, task.taskTitle, task.taskContent, task.taskCreatedAt,

                                        projectMember.projectMemberId, projectMember.projectMemberRole,
                                        milestone.milestoneName, GroupBy.list(tag.tagName)))
                ).get(0);
    }

    /**
     * 해당 작성자가 작성한 업무 리스트2
     *
     * @param projectSeq
     * @param projectMemberId
     * @return
     */
    @Override
    public List<TaskListDto> getTasks(Integer projectSeq, String projectMemberId) {
        QProjectMember projectMember = QProjectMember.projectMember;
        QTask task = QTask.task;

        return from(task)
                .innerJoin(task.projectMember, projectMember)
                .where(task.projectMember.project.projectSeq.eq(projectSeq))
                .where(task.projectMember.projectMemberId.eq(projectMemberId))
                .distinct()
                .orderBy(task.taskSeq.desc())
                .select(Projections.constructor(TaskListDto.class, task.taskSeq, task.taskTitle))
                .fetch();
    }

    @Override
    public ProjectMember getWriter(Integer taskSeq) {
        QTask task = QTask.task;
        QProjectMember projectMember = QProjectMember.projectMember;

        return from(task)
                .innerJoin(task.projectMember, projectMember)
                .where(task.taskSeq.eq(taskSeq))
                .select(projectMember)
                .fetchOne();
    }
}
