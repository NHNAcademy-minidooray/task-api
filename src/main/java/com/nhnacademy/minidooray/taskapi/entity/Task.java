package com.nhnacademy.minidooray.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_seq")
    private Integer taskSeq;

    @Column(name = "task_title")
    private String taskTitle;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "task_created_at")
    private LocalDateTime taskCreatedAt;

    @JoinColumn(name = "milestone_seq")
    @ManyToOne
    private Milestone milestone;

    @JoinColumn(name = "project_member_seq")
    @ManyToOne
    private ProjectMember projectMember;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<TaskTag> taskTags = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Task(String taskTitle, String taskContent, LocalDateTime taskCreatedAt, Milestone milestone, ProjectMember projectMember) {
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.taskCreatedAt = taskCreatedAt;
        this.milestone = milestone;
        this.projectMember = projectMember;
    }

    public void update(String taskTitle, String taskContent, Milestone milestone) {
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.milestone = milestone;
    }

}
