package com.nhnacademy.minidooray.taskapi.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Tags")
@Builder
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
}
