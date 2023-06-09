package com.nhnacademy.minidooray.taskapi.domain.request.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectRegisterRequest {
    @JsonProperty("title")
    private String projectTitle;
    @JsonProperty("content")
    private String projectContent;
}
