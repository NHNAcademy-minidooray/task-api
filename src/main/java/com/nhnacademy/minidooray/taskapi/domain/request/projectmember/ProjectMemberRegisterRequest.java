package com.nhnacademy.minidooray.taskapi.domain.request.projectmember;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ProjectMemberRegisterRequest {
    @JsonProperty("members")
    private List<Member> projectMemberIds = new ArrayList<>();
}
