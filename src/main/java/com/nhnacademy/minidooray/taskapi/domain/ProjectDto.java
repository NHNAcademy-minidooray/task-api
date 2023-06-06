package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.QueryAnnotation;

import java.time.LocalDateTime;

@Getter
public class ProjectDto {
    private Integer projectSeq;
    private String projectTitle;
    private String projectMemberId;
    private String projectMemberRole;
    private String statusCodeName;
    private String projectContent;
    private LocalDateTime projectCreatedAt;

    public ProjectDto(Integer projectSeq, String projectTitle, String projectMemberId, String projectMemberRole, String statusCodeName, String projectContent, LocalDateTime projectCreatedAt) {
        this.projectSeq = projectSeq;
        this.projectTitle = projectTitle;
        this.projectMemberId = projectMemberId;
        this.projectMemberRole = projectMemberRole;
        this.statusCodeName = statusCodeName;
        this.projectContent = projectContent;
        this.projectCreatedAt = projectCreatedAt;
    }
}
