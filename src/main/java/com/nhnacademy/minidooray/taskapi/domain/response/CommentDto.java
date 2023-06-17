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
@JsonRootName("comment")
public class CommentDto {

    @JsonProperty("id")
    private Integer commentSeq;

    @JsonProperty("writer")
    private String projectMemberId;
    @JsonProperty("content")
    private String commentContent;

}
