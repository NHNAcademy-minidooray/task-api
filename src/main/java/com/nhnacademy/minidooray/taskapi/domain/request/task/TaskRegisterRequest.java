package com.nhnacademy.minidooray.taskapi.domain.request.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskRegisterRequest {

    private String title;
    private String content;
    @JsonProperty("milestone_name")
    private String milestoneName;

    @JsonProperty("tag_names")
    private List<String> tagNames = new ArrayList<>();
}
