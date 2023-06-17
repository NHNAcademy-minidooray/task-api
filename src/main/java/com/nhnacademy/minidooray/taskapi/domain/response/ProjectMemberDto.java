package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMemberDto {
    @JsonProperty("id")
    private Integer projectMemberSeq;

    @JsonProperty("username")
    private String projectMemberId;

    @JsonProperty("auth")
    private String projectMemberRole;

}
