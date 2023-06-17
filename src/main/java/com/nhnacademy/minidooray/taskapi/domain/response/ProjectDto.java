package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonRootName("project")
public class ProjectDto {
    @JsonProperty("id")
    private Integer projectSeq;
    @JsonProperty("title")
    private String projectTitle;
    @JsonProperty("content")
    private String projectContent;

    @JsonProperty("created-at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime projectCreatedAt;

    @JsonProperty("member")
    private String projectMemberId;

    @JsonProperty("role")
    private String projectMemberRole;
    @JsonProperty("status")
    private String statusCodeName;

}
