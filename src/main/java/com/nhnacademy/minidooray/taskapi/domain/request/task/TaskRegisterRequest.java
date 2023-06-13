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
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @Nullable
    @JsonProperty("milestone")
    private String milestoneName;
    @Nullable
    private List<String> tagNames = new ArrayList<>();
}
