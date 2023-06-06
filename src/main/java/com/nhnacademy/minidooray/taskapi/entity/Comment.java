package com.nhnacademy.minidooray.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Comments")
@Getter
@Builder
@AllArgsConstructor
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

    @JoinColumn(name = "project_member_id")
    @ManyToOne
    private ProjectMember projectMember;
}
