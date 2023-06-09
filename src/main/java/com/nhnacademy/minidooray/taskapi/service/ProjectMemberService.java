package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.domain.request.projectmember.Member;
import com.nhnacademy.minidooray.taskapi.domain.request.projectmember.ProjectMemberRegisterRequest;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.exception.ForbiddenException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.projectmember.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;


    /**
     * 해당 프로젝트의 멤버 조회
     *
     * @param projectSeq 프로젝트 seq
     * @return 프로젝트 멤버 리스트
     */
    public List<ProjectMemberDto> getProjectMembers(Integer projectSeq) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로젝트입니다."));
        return projectMemberRepository.getProjectMemberList(projectSeq);
    }

    /**
     * 해당 프로젝트의 해당 계정 조회
     *
     * @param projectSeq      프로젝드 번호
     * @param projectMemberId 프로젝트 멤버 아이디
     * @return 해당 프로젝트의 해당 계정
     */
    public ProjectMemberDto getProjectMember(Integer projectSeq, String projectMemberId) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로젝트입니다."));

        ProjectMember projectMember = projectMemberRepository.findByProjectMemberId(projectMemberId).orElseThrow(
                () -> new NotFoundException("등록되지 않은 프로젝트 멤버입니다."));

        return projectMemberRepository.getProjectMember(projectSeq, projectMemberId);
    }

    /**
     * 해당 계정의 프로젝트 조회
     * @param projectMemberId
     * @return
     */
    public List<ProjectDto> getProjects(String projectMemberId) {
        ProjectMember projectMember = projectMemberRepository.findByProjectMemberId(projectMemberId).orElseThrow(
                () -> new NotFoundException("등록되지 않은 프로젝트 멤버입니다."));

        return projectMemberRepository.getProjects(projectMemberId);
    }



    /**
     * 프로젝트 멤버 생성
     * @param registerRequest 멤버 생성 요청 객체
     * @param projectSeq      멤버 생성 요청 객체
     * @param accountId   프로젝트 관리자 아이디 (프로젝트 등록 요청 아이디)
     * @return 생성된 프로젝트 멤버 객체
     */
    @Transactional
    public ProjectMemberDto createProjectMember(ProjectMemberRegisterRequest registerRequest,
                                                Integer projectSeq, String accountId) {
        if(!projectMemberRepository.findByProject_ProjectSeqAndProjectMemberRole(projectSeq,"ROLE_ADMIN")
                .equals(projectMemberRepository.findByProjectMemberId(accountId))) {
            throw new ForbiddenException("프로젝트 등록 권한이 없습니다.");
        }

        if (!projectRepository.existsById(registerRequest.getProjectSeq())) {
            throw new NotFoundException("존재하지 않는 프로젝트에 등록할 수 없습니다.");
        }

        ProjectMember projectMember = null;
        for(Member member : registerRequest.getProjectMemberIds()) {
            projectMember = ProjectMember.builder()
                                        .projectMemberId(member.getProjectMemberId())
                                        .project(projectRepository.getReferenceById(projectSeq))
                                        .projectMemberRole("ROLE_MEMBER")
                                        .build();

            projectMemberRepository.save(projectMember);
        }


        return projectMemberRepository.getProjectMember(projectSeq, projectMember.getProjectMemberId());
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
                () -> new NotFoundException("존재하지 않는 프로젝트를 삭제할 수 없습니다."));

        ProjectMember projectMember = projectMemberRepository.findByProjectMemberId(projectMemberId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로젝트 멤버입니다."));

        projectMemberRepository.deleteById(projectMember.getProjectMemberSeq());
    }
}
