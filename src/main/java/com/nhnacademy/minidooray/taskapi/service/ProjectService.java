package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.domain.request.ProjectModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.ProjectRegisterRequest;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.StatusCode;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundProjectException;
import com.nhnacademy.minidooray.taskapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    //todo 예외처리 custom 마무리
    private final ProjectRepository projectRepository;
    private final ProjectRepositoryImpl projectRepositoryCustom;
    private final StatusCodeRepository statusCodeRepository;
    private final ProjectMemberRepository projectMemberRepository;

    /**
     * 모든 프로젝트 조회
     * @return 프로젝트 리스트
     */
    public List<ProjectDto> getProjects() {
        return projectRepositoryCustom.findAllBy();
    }

    /**
     * 해당 프로젝트 조회
     * @param projectSeq 해당 프로젝트 seq
     * @return 해당 프로젝트
     */
    public ProjectDto getProject(Integer projectSeq) {
        return projectRepositoryCustom.findByProjectSeq(projectSeq);
    }


    /**
     * 프로젝트 생성, 프로젝트 관리자 생성
     * @param registerRequest 등록요청 내용
     * @return 생성된 프로젝트
     */
    @Transactional
    public ProjectDto createProject(ProjectRegisterRequest registerRequest, String accountId) {

        Project project = Project.builder()
                .projectCreatedAt(LocalDateTime.now())
                .projectContent(registerRequest.getProjectContent())
                .projectTitle(registerRequest.getProjectTitle())
                .statusCode(statusCodeRepository.getReferenceById(1))
                .build();

        projectRepository.save(project);

        // 프로젝트 생성자 -> 관리자로 저장
        ProjectMember projectMember = ProjectMember.builder()
                .project(project)
                .projectMemberId(accountId)
                .projectMemberRole("ROLE_ADMIN")
                .build();
        projectMemberRepository.save(projectMember);

        return projectRepositoryCustom.findByProjectSeq(project.getProjectSeq());

    }

    /**
     * 프로젝트 수정
     * @param modifyRequest 수정요청 내용
     * @param projectSeq 수정할 프로젝트 seq
     * @return 수정된 프로젝트
     */
    @Transactional
    public ProjectDto modifyProject(ProjectModifyRequest modifyRequest, Integer projectSeq) {
        Project project = projectRepository.findById(projectSeq).orElseThrow(() -> new NotFoundProjectException("존재하지 않는 프로젝트입니다."));

        project.setProjectContent(modifyRequest.getProjectContent());
        project.setProjectTitle(modifyRequest.getProjectTitle());

        projectRepository.saveAndFlush(project);

        return projectRepositoryCustom.findByProjectSeq(projectSeq);
    }

    @Transactional
    public void deleteProject(Integer projectSeq) {
        projectRepository.findById(projectSeq).orElseThrow(() -> new NotFoundProjectException("존재하지 않는 프로젝트입니다."));
        projectRepository.deleteById(projectSeq);
    }

}
