package com.nhnacademy.minidooray.taskapi.domain.request.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class TaskModifyRequest {
    @NotBlank(message = "title : 필수 입력값 입니다.")
    private String title;
    @NotBlank(message = "content : 필수 입력값 입니다.")
    private String content;

    @JsonProperty("milestone")
    private String milestoneName;
    private List<String> tagNames = new ArrayList<>();
}
