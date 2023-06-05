package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.QProject;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl extends QuerydslRepositorySupport implements ProjectRepositoryCustom {
    public ProjectRepositoryImpl() {
        super(Project.class);
    }

    @Override
    public List<ProjectDto> findAllBy() {
        QProject project = QProject.project;
        return from(project)
                .select(Projections.constructor(ProjectDto.class, project.projectSeq,
                        project.projectTitle, project.statusCode.statusCodeName,
                        project.projectContent, project.projectCreatedAt)).fetch();
    }

    @Override
    public ProjectDto findByProjectSeq(Integer projectSeq) {
        QProject project = QProject.project;

        return from(project)
                .where(project.projectSeq.eq(projectSeq))
                .select(Projections.constructor(ProjectDto.class, project.projectSeq,
                        project.projectTitle, project.statusCode.statusCodeName,
                        project.projectContent, project.projectCreatedAt)).fetch().get(0);
    }
}
