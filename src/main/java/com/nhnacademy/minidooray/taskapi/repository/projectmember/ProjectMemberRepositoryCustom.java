package com.nhnacademy.minidooray.taskapi.repository.projectmember;

import com.nhnacademy.minidooray.taskapi.domain.response.MemberListDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.response.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ProjectMemberRepositoryCustom {
    List<ProjectMemberDto> getProjectMemberList(Integer projectSeq);
    Optional<ProjectMemberDto> getProjectMember(Integer projectSeq, String projectMemberId);
    List<ProjectDto> getProjects(String projectMemberId);
    List<ProjectMember> findByProjectMemberId(String projectMemberId);
    MemberListDto getProjectMember(Integer projectMemberSeq);
}
