package com.nhnacademy.minidooray.taskapi.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectModifyRequest {
    private String projectTitle;
    private String projectContent;
}
