package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ProjectMemberDto {
    @JsonProperty("id")
    private Integer projectMemberSeq;

    @JsonProperty("username")
    private String projectMemberId;

    @JsonProperty("auth")
    private String projectMemberRole;

    public ProjectMemberDto(Integer projectMemberSeq, String projectMemberId, String projectMemberRole) {
        this.projectMemberSeq = projectMemberSeq;
        this.projectMemberId = projectMemberId;
        this.projectMemberRole = projectMemberRole;
    }
}
