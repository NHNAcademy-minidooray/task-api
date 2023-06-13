package com.nhnacademy.minidooray.taskapi.domain.request.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectRegisterRequest {
    @NotBlank
    @JsonProperty("title")
    private String projectTitle;

    @NotBlank
    @JsonProperty("content")
    private String projectContent;
}
