package com.nhnacademy.minidooray.taskapi.domain.request.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectModifyRequest {
    @JsonProperty("title")
    private String projectTitle;
    @JsonProperty("content")
    private String projectContent;
}
