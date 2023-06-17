package com.nhnacademy.minidooray.taskapi.domain.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("milestone")
public class MilestoneDto {
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

}
