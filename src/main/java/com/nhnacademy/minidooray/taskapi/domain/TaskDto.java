package com.nhnacademy.minidooray.taskapi.domain;

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
    private Integer taskSeq;
    @NotBlank
    private String taskTitle;
    @NotBlank
    private String taskContent;
    @NotNull
    private LocalDateTime taskCreatedAt;
    @NotBlank
    private String projectMemberId;
    @NotBlank
    private String projectMemberRole;
    @Nullable
    private String milestoneName;

    @Nullable
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
