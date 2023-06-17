package com.nhnacademy.minidooray.taskapi.domain.request.projectmember;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class Member {
    @NotBlank(message = "id : 필수 입력값 입니다.")
    @JsonProperty("id")
    private String projectMemberId;
}
