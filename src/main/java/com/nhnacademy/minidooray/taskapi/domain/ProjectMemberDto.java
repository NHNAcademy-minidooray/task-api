package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ProjectMemberDto {
    @NotNull
    private Integer projectMemberSeq;
    @NotBlank
    private String projectMemberId;

    @NotBlank
    private String projectMemberRole;

    public ProjectMemberDto(Integer projectMemberSeq, String projectMemberId, String projectMemberRole) {
        this.projectMemberSeq = projectMemberSeq;
        this.projectMemberId = projectMemberId;
        this.projectMemberRole = projectMemberRole;
    }
}
