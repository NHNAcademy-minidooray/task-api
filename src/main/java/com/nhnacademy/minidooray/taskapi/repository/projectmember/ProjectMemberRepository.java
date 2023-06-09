package com.nhnacademy.minidooray.taskapi.repository.projectmember;

import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Integer>, ProjectMemberRepositoryCustom {
    Optional<ProjectMember> findByProjectMemberId(String projectMemberId);
    ProjectMember findByProject_ProjectSeqAndProjectMemberRole(Integer projectSeq, String projectMemberRole);
}
