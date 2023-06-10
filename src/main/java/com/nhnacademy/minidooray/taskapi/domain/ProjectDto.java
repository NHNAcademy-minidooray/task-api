package com.nhnacademy.minidooray.taskapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.QueryAnnotation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@JsonRootName("project")
public class ProjectDto {
    @NotNull
    @JsonProperty("id")
    private Integer projectSeq;
    @NotBlank
    @JsonProperty("title")
    private String projectTitle;

    @NotBlank
    @JsonProperty("content")
    private String projectContent;

    @NotNull
    @JsonProperty("created-at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime projectCreatedAt;

    @NotBlank
    @JsonProperty("member")
    private String projectMemberId;

    @NotBlank
    @JsonProperty("role")
    private String projectMemberRole;
    @NotBlank
    @JsonProperty("status")
    private String statusCodeName;


    public ProjectDto(Integer projectSeq, String projectTitle, String projectMemberId, String projectMemberRole,
                      String statusCodeName, String projectContent, LocalDateTime projectCreatedAt) {
        this.projectSeq = projectSeq;
        this.projectTitle = projectTitle;
        this.projectMemberId = projectMemberId;
        this.projectMemberRole = projectMemberRole;
        this.statusCodeName = statusCodeName;
        this.projectContent = projectContent;
        this.projectCreatedAt = projectCreatedAt;
    }
}
