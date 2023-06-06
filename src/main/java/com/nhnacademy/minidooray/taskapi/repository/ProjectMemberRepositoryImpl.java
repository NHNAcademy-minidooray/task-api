package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.QProject;
import com.nhnacademy.minidooray.taskapi.entity.QProjectMember;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProjectMemberRepositoryImpl extends QuerydslRepositorySupport implements ProjectMemberRepositoryCustom {
    public ProjectMemberRepositoryImpl() {
        super(ProjectMember.class);
    }

    @Override
    public List<ProjectMemberDto> getProjectMemberList(Integer projectSeq) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;

        return from(projectMember)
                .innerJoin(projectMember.project, project)
                .where(projectMember.project.projectSeq.eq(projectSeq))
                .select(Projections.constructor(ProjectMemberDto.class, projectMember.projectMemberSeq,
                        projectMember.projectMemberId, projectMember.projectMemberRole))
                .fetch();
    }

    @Override
    public ProjectMemberDto getProjectMember(Integer projectSeq, String projectMemberId) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;

        return from(projectMember)
                .innerJoin(projectMember.project, project)
                .where(projectMember.project.projectSeq.eq(projectSeq))
                .where(projectMember.projectMemberId.eq(projectMemberId))
                .select(Projections.constructor(ProjectMemberDto.class, projectMember.projectMemberSeq,
                        projectMember.projectMemberId, projectMember.projectMemberRole))
                .fetch().get(0);
    }

    @Override
    public List<ProjectDto> getProjects(String projectMemberId) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;

        return from(projectMember)
                .innerJoin(projectMember.project, project)
                .where(projectMember.projectMemberId.eq(projectMemberId))
                .select(Projections.constructor(ProjectDto.class, project.projectSeq, project.projectTitle,
                        projectMember.projectMemberId, projectMember.projectMemberRole,
                        project.statusCode.statusCodeName, project.projectContent, project.projectCreatedAt))
                .fetch();
    }
}
