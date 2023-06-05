package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.QueryAnnotation;

import java.time.LocalDateTime;

@Getter
public class ProjectDto {
    private Integer projectSeq;
    private String projectTitle;
    private String statusCodeName;
    private String projectContent;
    private LocalDateTime projectCreatedAt;

    public ProjectDto(Integer projectSeq, String projectTitle, String statusCodeName, String projectContent, LocalDateTime projectCreatedAt) {
        this.projectSeq = projectSeq;
        this.projectTitle = projectTitle;
        this.statusCodeName = statusCodeName;
        this.projectContent = projectContent;
        this.projectCreatedAt = projectCreatedAt;
    }
}
