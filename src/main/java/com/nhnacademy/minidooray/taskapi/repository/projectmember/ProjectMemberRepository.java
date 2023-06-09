package com.nhnacademy.minidooray.taskapi.repository.projectmember;

import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Integer>, ProjectMemberRepositoryCustom {
    ProjectMember findByProjectMemberIdAndProject_ProjectSeq(String projectMemberId, Integer projectSeq);
    ProjectMember findByProject_ProjectSeqAndProjectMemberRole(Integer projectSeq, String projectMemberRole);
}
