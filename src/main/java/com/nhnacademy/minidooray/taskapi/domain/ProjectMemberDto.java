package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;

@Getter
public class ProjectMemberDto {
    private Integer projectMemberSeq;
    private String projectMemberId;
    private String projectMemberRole;

    public ProjectMemberDto(Integer projectMemberSeq, String projectMemberId, String projectMemberRole) {
        this.projectMemberSeq = projectMemberSeq;
        this.projectMemberId = projectMemberId;
        this.projectMemberRole = projectMemberRole;
    }
}
