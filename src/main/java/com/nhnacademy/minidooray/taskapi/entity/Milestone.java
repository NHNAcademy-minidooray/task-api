package com.nhnacademy.minidooray.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Milestones")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_seq")
    private Integer milestoneSeq;

    @Column(name = "milestone_name")
    private String milestoneName;

    @Column(name = "milestone_start_period")
    private LocalDateTime milestoneStartPeriod;

    @Column(name = "milestone_end_of_period")
    private LocalDateTime milestoneEndOfPeriod;

    @JoinColumn(name = "project_seq")
    @ManyToOne
    private Project project;

}
