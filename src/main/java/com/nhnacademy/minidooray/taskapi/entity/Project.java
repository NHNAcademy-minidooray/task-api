package com.nhnacademy.minidooray.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Entity
@Table(name = "Projects")
@Builder
@AllArgsConstructor
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


}
