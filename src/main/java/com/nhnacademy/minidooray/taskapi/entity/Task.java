package com.nhnacademy.minidooray.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
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

}
