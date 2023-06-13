package com.nhnacademy.minidooray.taskapi.repository.projectmember;

import com.nhnacademy.minidooray.taskapi.domain.request.projectmember.Member;
import com.nhnacademy.minidooray.taskapi.domain.response.MemberListDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.QProject;
import com.nhnacademy.minidooray.taskapi.entity.QProjectMember;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

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
    public Optional<ProjectMemberDto> getProjectMember(Integer projectSeq, String projectMemberId) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;

        return from(projectMember)
                .innerJoin(projectMember.project, project)
                .where(projectMember.project.projectSeq.eq(projectSeq))
                .where(projectMember.projectMemberId.eq(projectMemberId))
                .select(Projections.constructor(ProjectMemberDto.class, projectMember.projectMemberSeq,
                        projectMember.projectMemberId, projectMember.projectMemberRole))
                .fetch().stream().findFirst();
    }

    @Override
    public List<ProjectDto> getProjects(String projectMemberId) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;

        return from(projectMember)
                .innerJoin(projectMember.project, project)
                .where(projectMember.projectMemberId.eq(projectMemberId))
                .select(Projections.constructor(ProjectDto.class, project.projectSeq, project.projectTitle,
                        project.projectContent, project.projectCreatedAt,
                        projectMember.projectMemberId, projectMember.projectMemberRole,
                        project.statusCode.statusCodeName))
                .fetch();
    }

    @Override
    public List<ProjectMember> findByProjectMemberId(String projectMemberId) {
        QProjectMember projectMember = QProjectMember.projectMember;
        return from(projectMember)
                .where(projectMember.projectMemberId.eq(projectMemberId))
                .select(projectMember)
                .fetch();
    }

    @Override
    public MemberListDto getProjectMember(Integer projectMemberSeq) {
        QProjectMember projectMember = QProjectMember.projectMember;
        return from(projectMember)
                .where(projectMember.projectMemberSeq.eq(projectMemberSeq))
                .select(Projections.constructor(MemberListDto.class, projectMember.projectMemberSeq))
                .fetchOne();
    }
}
