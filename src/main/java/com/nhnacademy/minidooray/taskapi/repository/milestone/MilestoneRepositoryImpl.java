package com.nhnacademy.minidooray.taskapi.repository.milestone;


import com.nhnacademy.minidooray.taskapi.domain.response.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class MilestoneRepositoryImpl extends QuerydslRepositorySupport implements MilestoneRepositoryCustom{
    public MilestoneRepositoryImpl() {
        super(Milestone.class);
    }

    @Override
    public List<MilestoneDto> getMilestones(Integer projectSeq) {
        QProject project = QProject.project;
        QMilestone milestone = QMilestone.milestone;

        return from(milestone)
                .innerJoin(milestone.project, project)
                .where(project.projectSeq.eq(projectSeq))
                .select(Projections.constructor(MilestoneDto.class, milestone.milestoneSeq,
                        milestone.milestoneName, milestone.milestoneStartPeriod, milestone.milestoneEndOfPeriod))
                .fetch();
    }

    @Override
    public Optional<MilestoneDto> getMileStone(Integer milestoneSeq) {
        QProject project = QProject.project;
        QMilestone milestone = QMilestone.milestone;

        return from(milestone)
                .innerJoin(milestone.project, project)
                .where(milestone.milestoneSeq.eq(milestoneSeq))
                .select(Projections.constructor(MilestoneDto.class, milestone.milestoneSeq,
                        milestone.milestoneName, milestone.milestoneStartPeriod, milestone.milestoneEndOfPeriod))
                .stream().findFirst();
    }

    @Override
    public List<TaskListDto> getTasks(Integer projectSeq, Integer milestoneSeq) {
        QTask task = QTask.task;
        QProjectMember projectMember = QProjectMember.projectMember;
        QMilestone milestone = QMilestone.milestone;

        return from(task)
                .innerJoin(task.milestone, milestone)
                .innerJoin(task.projectMember, projectMember)
                .where(milestone.milestoneSeq.eq(milestoneSeq))
                .distinct()
                .select(Projections.constructor(TaskListDto.class, task.taskSeq, task.taskTitle,
                        projectMember.project.projectSeq, projectMember.project.projectTitle))
                .fetch();
    }
}
