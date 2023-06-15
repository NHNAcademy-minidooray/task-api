package com.nhnacademy.minidooray.taskapi.domain.request.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskRegisterRequest {
    @NotBlank(message = "title : 필수 입력값 입니다.")
    private String title;
    @NotBlank(message = "content : 필수 입력값 입니다.")
    private String content;
    @Nullable
    @JsonProperty("milestone")
    private String milestoneName;
    @Nullable
    private List<String> tagNames = new ArrayList<>();
}
