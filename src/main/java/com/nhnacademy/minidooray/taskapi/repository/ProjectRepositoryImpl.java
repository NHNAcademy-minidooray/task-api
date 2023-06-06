package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.QProject;
import com.nhnacademy.minidooray.taskapi.entity.QProjectMember;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl extends QuerydslRepositorySupport implements ProjectRepositoryCustom {
    public ProjectRepositoryImpl() {
        super(Project.class);
    }

    /**
     * 전체 프로젝트 조회
     * @return 프로젝트 리스트
     */
    @Override
    public List<ProjectDto> findAllBy() {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        return from(projectMember)
                .innerJoin(projectMember.project, project)
                .where(projectMember.projectMemberRole.eq("ROLE_ADMIN"))
                .orderBy(project.projectSeq.asc())
                .select(Projections.constructor(ProjectDto.class, project.projectSeq,
                        project.projectTitle, projectMember.projectMemberId, projectMember.projectMemberRole,
                        project.statusCode.statusCodeName, project.projectContent, project.projectCreatedAt))
                .fetch();
    }

    /**
     * 해당 프로젝트 조회
     * @param projectSeq 프로젝트 seq
     * @return 해당 프로젝트
     */
    @Override
    public ProjectDto findByProjectSeq(Integer projectSeq) {
        QProject project = QProject.project;
        QProjectMember projectMember = QProjectMember.projectMember;
        return from(projectMember)
                .innerJoin(projectMember.project, project)
                .where(project.projectSeq.eq(projectSeq))
                .select(Projections.constructor(ProjectDto.class, project.projectSeq,
                        project.projectTitle,projectMember.projectMemberId, projectMember.projectMemberRole,
                        project.statusCode.statusCodeName, project.projectContent,
                        project.projectCreatedAt))
                .fetch().get(0);
    }
}
