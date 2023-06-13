package com.nhnacademy.minidooray.taskapi.repository.project;

import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface ProjectRepositoryCustom {
    List<ProjectDto> findAllBy();
    ProjectDto findByProjectSeq(Integer projectSeq);

}
