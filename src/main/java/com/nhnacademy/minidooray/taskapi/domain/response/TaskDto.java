package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@JsonRootName("task")
public class TaskDto {
    @JsonProperty("id")
    private Integer taskSeq;
    @JsonProperty("title")
    private String taskTitle;
    @JsonProperty("content")
    private String taskContent;
    @JsonProperty("created-at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskCreatedAt;

    @JsonProperty("writer")
    private String projectMemberId;
    @JsonProperty("role")
    private String projectMemberRole;
    @JsonProperty("milestone")
    private String milestoneName;
    @JsonProperty("tag")
    private List<String> tagNames = new ArrayList<>();

    public TaskDto(Integer taskSeq, String taskTitle, String taskContent, LocalDateTime taskCreatedAt, String projectMemberId, String projectMemberRole, String milestoneName, List<String> tagNames) {
        this.taskSeq = taskSeq;
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.taskCreatedAt = taskCreatedAt;
        this.projectMemberId = projectMemberId;
        this.projectMemberRole = projectMemberRole;
        this.milestoneName = milestoneName;
        if(!Objects.isNull(tagNames)) {
            this.tagNames = tagNames;
        }
    }
}
