package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@JsonRootName("task")
public class TaskListDto {
    @JsonProperty("id")
    private Integer taskSeq;
    @JsonProperty("title")
    private String taskTitle;

    public TaskListDto(Integer taskSeq, String taskTitle) {
        this.taskSeq = taskSeq;
        this.taskTitle = taskTitle;
    }
}
