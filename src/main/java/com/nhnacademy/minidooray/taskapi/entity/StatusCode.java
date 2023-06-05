package com.nhnacademy.minidooray.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "StatusCodes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_code_seq")
    private Integer statusCodeSeq;

    @Column(name = "status_code_name")
    private String statusCodeName;
}
