package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.NoRepositoryBean;

import java.nio.file.LinkOption;
import java.util.List;
import java.util.Optional;


/**
 * //### 전체 프로젝트 조회
 * GET /projects
 *
 * //### ID로 해당 프로젝트 조회
 * GET /projects/{id}
 *
 *
 * //### 프로젝트 생성
 * POST /projects
 *
 *
 * //### ID로 해당 프로젝트 업데이트
 * PATCH /projects/{id}
 *
 *
 * //### ID로 해당 프로젝트 삭제
 * DELETE /projects/{id}
 */

@NoRepositoryBean
public interface ProjectRepositoryCustom {
    List<ProjectDto> findAllBy();
    ProjectDto findByProjectSeq(Integer projectSeq);

}
