package com.nhnacademy.minidooray.taskapi.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberRegisterRequest {
    private String projectMemberId;
    private Integer projectSeq;
}
