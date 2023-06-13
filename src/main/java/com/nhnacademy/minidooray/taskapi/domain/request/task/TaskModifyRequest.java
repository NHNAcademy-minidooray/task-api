package com.nhnacademy.minidooray.taskapi.domain.request.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskModifyRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    @JsonProperty("milestone")
    private String milestoneName;
    private List<String> tagNames = new ArrayList<>();
}
