package com.nhnacademy.minidooray.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PojectMembers")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_seq)")
    private Integer projectMemberSeq;

    @Column(name = "project_member_id")
    private String projectMemberId;

    @JoinColumn(name = "project_seq")
    @ManyToOne
    private Project project;

    @Column(name = "project_member_role")
    private String projectMemberRole;
}
