package com.nhnacademy.minidooray.taskapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

@Entity
@Table(name = "TaskTags")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskTag {

    @EmbeddedId
    private  Pk pk;

    @JsonIgnore
    @JoinColumn(name = "tag_seq")
    @MapsId("tagSeq")
    @ManyToOne
    private Tag tag;

    @JsonIgnore
    @JoinColumn(name = "task_seq")
    @MapsId("taskSeq")
    @ManyToOne
    private Task task;


    @Embeddable
    @EqualsAndHashCode
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pk implements Serializable {
        @Column(name = "tag_seq")
        private Integer tagSeq;

        @Column(name = "task_seq")
        private Integer taskSeq;
    }
}
