package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("tag")
public class TagDto {
    @JsonProperty("id")
    private Integer tagSeq;

    @JsonProperty("name")
    private String tagName;

    @JsonProperty("project_id")
    private Integer projectSeq;

}
