package com.nhnacademy.minidooray.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "ProjectMembers")
@Getter
@NoArgsConstructor
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_seq")
    private Integer projectMemberSeq;

    @Column(name = "project_member_id", unique = true)
    private String projectMemberId;

    @JoinColumn(name = "project_seq")
    @ManyToOne
    private Project project;

    @Column(name = "project_member_role")
    private String projectMemberRole;

    @Builder
    public ProjectMember(String projectMemberId, Project project, String projectMemberRole) {
        this.projectMemberId = projectMemberId;
        this.project = project;
        this.projectMemberRole = projectMemberRole;
    }
}
