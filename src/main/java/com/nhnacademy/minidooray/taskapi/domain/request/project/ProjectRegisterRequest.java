package com.nhnacademy.minidooray.taskapi.domain.request.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRegisterRequest {
    @NotBlank(message = "title : 필수 입력값 입니다.")
    @JsonProperty("title")
    private String projectTitle;

    @NotBlank(message = "content : 필수 입력값 입니다.")
    @JsonProperty("content")
    private String projectContent;
}
