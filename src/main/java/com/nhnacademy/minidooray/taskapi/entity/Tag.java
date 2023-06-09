package com.nhnacademy.minidooray.taskapi.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Tags")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Tag {
    @Id
    @Column(name = "tag_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagSeq;

    @Column(name = "tag_name")
    private String tagName;

    @JoinColumn(name = "project_seq")
    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "tag", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<TaskTag> taskTags;

    @Builder
    public Tag(String tagName, Project project) {
        this.tagName = tagName;
        this.project = project;
    }

    public void update(String tagName) {
        this.tagName = tagName;
    }
}
