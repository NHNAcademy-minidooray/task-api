package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.request.ProjectModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.ProjectRegisterRequest;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.StatusCode;
import com.nhnacademy.minidooray.taskapi.repository.CommentRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepositoryImpl;
import com.nhnacademy.minidooray.taskapi.repository.StatusCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    //todo 예외처리 custom 마무리
    private final ProjectRepository projectRepository;
    private final ProjectRepositoryImpl projectRepositoryCustom;
    private final StatusCodeRepository statusCodeRepository;

    public List<ProjectDto> getProjects() {
        return projectRepositoryCustom.findAllBy();
    }

    public ProjectDto getProject(Integer projectSeq) {
        return projectRepositoryCustom.findByProjectSeq(projectSeq);
    }

    public ProjectDto createProject(ProjectRegisterRequest registerRequest) {

        Project project = Project.builder()
                .projectCreatedAt(LocalDateTime.now())
                .projectContent(registerRequest.getProjectContent())
                .projectTitle(registerRequest.getProjectTitle())
                .statusCode(statusCodeRepository.getReferenceById(1))
                .build();

        projectRepository.save(project);

        return projectRepositoryCustom.findByProjectSeq(project.getProjectSeq());

    }

    public ProjectDto modifyProject(ProjectModifyRequest modifyRequest, Integer projectSeq) {
        Project project = projectRepository.findById(projectSeq).orElseThrow(() -> new RuntimeException("존재하지 않는 프로젝트입니다."));

        project.setProjectContent(modifyRequest.getProjectContent());
        project.setProjectTitle(modifyRequest.getProjectTitle());

        projectRepository.saveAndFlush(project);

        return projectRepositoryCustom.findByProjectSeq(projectSeq);
    }

    public void deleteProject(Integer projectSeq) {
        projectRepository.findById(projectSeq).orElseThrow(() -> new RuntimeException("존재하지 않는 프로젝트입니다."));
        projectRepository.deleteById(projectSeq);
    }

}
