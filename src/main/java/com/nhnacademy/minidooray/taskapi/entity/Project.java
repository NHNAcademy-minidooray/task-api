package com.nhnacademy.minidooray.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Projects")
@NoArgsConstructor
@Getter
public class Project {
    @Id
    @Column(name = "project_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectSeq;

    @Setter
    @Column(name = "project_title")
    private String projectTitle;

    @JoinColumn(name = "status_code_seq")
    @ManyToOne
    private StatusCode statusCode;

    @Setter
    @Column(name = "project_content")
    private String projectContent;

    @Column(name = "project_created_at")
    private LocalDateTime projectCreatedAt;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE})
    private List<ProjectMember> projectMembers;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.REMOVE})
    private List<Tag> tags;

    @Builder
    public Project(String projectTitle, StatusCode statusCode, String projectContent, LocalDateTime projectCreatedAt) {
        this.projectTitle = projectTitle;
        this.statusCode = statusCode;
        this.projectContent = projectContent;
        this.projectCreatedAt = projectCreatedAt;
    }
}
