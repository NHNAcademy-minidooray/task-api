package com.nhnacademy.minidooray.taskapi.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectRegisterRequest {
    private String projectTitle;
    private String projectContent;
}
