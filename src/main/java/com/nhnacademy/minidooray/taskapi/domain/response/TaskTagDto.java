package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@JsonRootName("task-tag")
public class TaskTagDto {
    @JsonProperty("task-id")
    private Integer taskSeq;

    @JsonProperty("tag-id")
    private Integer tagSeq;

    public TaskTagDto(Integer taskSeq, Integer tagSeq) {
        this.taskSeq = taskSeq;
        this.tagSeq = tagSeq;
    }
}
