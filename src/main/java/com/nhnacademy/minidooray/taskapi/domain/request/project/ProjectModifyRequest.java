package com.nhnacademy.minidooray.taskapi.domain.request.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjectModifyRequest {
    @NotBlank
    @JsonProperty("title")
    private String projectTitle;
    @NotBlank
    @JsonProperty("content")
    private String projectContent;
}
