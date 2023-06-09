package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.QueryAnnotation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class ProjectDto {
    @NotNull
    private Integer projectSeq;
    @NotBlank
    private String projectTitle;
    @NotBlank
    private String projectMemberId;
    @NotBlank
    private String projectMemberRole;
    @NotBlank
    private String statusCodeName;
    @NotBlank
    private String projectContent;
    @NotNull
    private LocalDateTime projectCreatedAt;

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
