package com.nhnacademy.minidooray.taskapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Milestones")
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate milestoneStartPeriod;

    @Column(name = "milestone_end_of_period")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate milestoneEndOfPeriod;

    @JoinColumn(name = "project_seq")
    @ManyToOne
    private Project project;

    @Builder
    public Milestone(String milestoneName, LocalDate milestoneStartPeriod, LocalDate milestoneEndOfPeriod, Project project) {
        this.milestoneName = milestoneName;
        this.milestoneStartPeriod = milestoneStartPeriod;
        this.milestoneEndOfPeriod = milestoneEndOfPeriod;
        this.project = project;
    }


    public void update(String milestoneName, LocalDate milestoneStartPeriod, LocalDate milestoneEndOfPeriod) {
        this.milestoneName = milestoneName;
        this.milestoneStartPeriod = milestoneStartPeriod;
        this.milestoneEndOfPeriod = milestoneEndOfPeriod;
    }
}
