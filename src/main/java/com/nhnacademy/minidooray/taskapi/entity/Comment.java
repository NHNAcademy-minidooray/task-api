package com.nhnacademy.minidooray.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Comments")
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq")
    private Integer commentSeq;

    @JoinColumn(name = "task_seq")
    @ManyToOne
    private Task task;

    @Column(name = "comment_content")
    private String commentContent;

    @JoinColumn(name = "project_member_seq")
    @ManyToOne
    private ProjectMember projectMember;


    @Builder
    public Comment(Task task, String commentContent, ProjectMember projectMember) {
        this.task = task;
        this.commentContent = commentContent;
        this.projectMember = projectMember;
    }

    public void update(String commentContent) {
        this.commentContent = commentContent;
    }
}
