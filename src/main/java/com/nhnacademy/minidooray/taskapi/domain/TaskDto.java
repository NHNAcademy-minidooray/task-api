package com.nhnacademy.minidooray.taskapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonRootName("task")
public class TaskDto {
    @NotNull
    @JsonProperty("id")
    private Integer taskSeq;
    @NotBlank
    @JsonProperty("title")
    private String taskTitle;
    @NotBlank
    @JsonProperty("content")
    private String taskContent;
    @NotNull
    @JsonProperty("created-at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskCreatedAt;
    @NotBlank
    @JsonProperty("writer")
    private String projectMemberId;
    @NotBlank
    @JsonProperty("role")
    private String projectMemberRole;
    @Nullable
    @JsonProperty("milestone")
    private String milestoneName;

    @Nullable
    @JsonProperty("tag")
    private List<String> tagNames = new ArrayList<>();

    public TaskDto(Integer taskSeq, String taskTitle, String taskContent, LocalDateTime taskCreatedAt,
                   String projectMemberId, String projectMemberRole, @Nullable String milestoneName,
                   @Nullable List<TaskTag> tagNames) {
        this.taskSeq = taskSeq;
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.taskCreatedAt = taskCreatedAt;
        this.projectMemberId = projectMemberId;
        this.projectMemberRole = projectMemberRole;
        this.milestoneName = milestoneName;
        if(tagNames.size() > 0) {
            tagNames.forEach(x -> this.tagNames.add(x.getTag().getTagName()));
        }
    }
}
