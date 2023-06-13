package com.nhnacademy.minidooray.taskapi.domain.request.projectmember;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Member {
    @NotBlank
    @JsonProperty("id")
    private String projectMemberId;
}
