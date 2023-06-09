package com.nhnacademy.minidooray.taskapi.repository.projectmember;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProjectMemberRepositoryCustom {
    List<ProjectMemberDto> getProjectMemberList(Integer projectSeq);
    ProjectMemberDto getProjectMember(Integer projectSeq, String projectMemberId);
    List<ProjectDto> getProjects(String projectMemberId);
}
