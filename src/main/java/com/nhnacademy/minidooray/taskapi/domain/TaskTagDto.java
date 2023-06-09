package com.nhnacademy.minidooray.taskapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@JsonRootName("task-tag")
public class TaskTagDto {
    @NotNull
    @JsonProperty("task-id")
    private Integer taskSeq;

    @NotNull
    @JsonProperty("tag-id")
    private Integer tagSeq;

    public TaskTagDto(Integer taskSeq, Integer tagSeq) {
        this.taskSeq = taskSeq;
        this.tagSeq = tagSeq;
    }
}
