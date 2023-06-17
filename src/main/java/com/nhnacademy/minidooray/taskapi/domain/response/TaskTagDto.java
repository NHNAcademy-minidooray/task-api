package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("task-tag")
public class TaskTagDto {
    @JsonProperty("task-id")
    private Integer taskSeq;

    @JsonProperty("tag-id")
    private Integer tagSeq;
}
