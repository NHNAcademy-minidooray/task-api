package com.nhnacademy.minidooray.taskapi.domain.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonRootName("milestone")
public class MilestoneDto {
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    public MilestoneDto(Integer id, String name, LocalDate start, LocalDate end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }
}
