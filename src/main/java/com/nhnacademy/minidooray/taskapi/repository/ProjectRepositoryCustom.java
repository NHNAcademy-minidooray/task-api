package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.NoRepositoryBean;

import java.nio.file.LinkOption;
import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public interface ProjectRepositoryCustom {
    List<ProjectDto> findAllBy();
    ProjectDto findByProjectSeq(Integer projectSeq);

}
