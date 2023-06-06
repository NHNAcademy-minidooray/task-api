package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.domain.request.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundProjectException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundProjectMemberException;
import com.nhnacademy.minidooray.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectMemberRepositoryImpl;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberRepositoryImpl projectMemberRepositoryCustom;
    private final ProjectRepository projectRepository;


    /**
     * 해당 프로젝트의 멤버 조회
     *
     * @param projectSeq 프로젝트 seq
     * @return 프로젝트 멤버 리스트
     */
    public List<ProjectMemberDto> getProjectMembers(Integer projectSeq) {
        return projectMemberRepositoryCustom.getProjectMemberList(projectSeq);
    }

    /**
     * 해당 프로젝트의 해당 계정 조회
     *
     * @param projectSeq      프로젝드 번호
     * @param projectMemberId 프로젝트 멤버 아이디
     * @return 해당 프로젝트의 해당 계정
     */
    public ProjectMemberDto getProjectMember(Integer projectSeq, String projectMemberId) {
        return projectMemberRepositoryCustom.getProjectMember(projectSeq, projectMemberId);
    }

    /**
     * 해당 계정의 프로젝트 조회
     * @param accountId
     * @return
     */
    public List<ProjectDto> getProjects(String accountId) {
        return projectMemberRepositoryCustom.getProjects(accountId);
    }


    /**
     * 프로젝트 멤버 생성
     *
     * @param registerRequest 멤버 생성 요청 객체
     * @param projectSeq      해당 프로젝트 번호
     * @return 생성된 프로젝트 멤버 객체
     */
    @Transactional
    public ProjectMemberDto createProjectMember(ProjectMemberRegisterRequest registerRequest, Integer projectSeq) {
        if (!projectRepository.existsById(registerRequest.getProjectSeq())) {
            throw new NotFoundProjectException("존재하지 않는 프로젝트에 등록할 수 없습니다.");
        }

        ProjectMember projectMember = ProjectMember.builder()
                .projectMemberId(registerRequest.getProjectMemberId())
                .project(projectRepository.getReferenceById(projectSeq))
                .projectMemberRole("ROLE_MEMBER")
                .build();

        projectMemberRepository.save(projectMember);

        return projectMemberRepositoryCustom.getProjectMember(projectSeq, projectMember.getProjectMemberId());
    }

    /**
     * 프로젝트 멤버 삭제
     *
     * @param projectSeq      해당 프로젝트 번호
     * @param projectMemberId 해당 프로젝트의 멤버 아이디
     */
    @Transactional
    public void deleteProjectMember(Integer projectSeq, String projectMemberId) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundProjectException("존재하지 않는 프로젝트를 삭제할 수 없습니다."));

        ProjectMember projectMember = projectMemberRepository.findByProjectMemberId(projectMemberId).orElseThrow(
                () -> new NotFoundProjectMemberException("존재하지 않는 프로젝트 멤버입니다."));

        projectMemberRepository.deleteById(projectMember.getProjectMemberSeq());
    }
}
