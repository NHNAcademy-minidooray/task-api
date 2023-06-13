package com.nhnacademy.minidooray.taskapi.domain.request.milestone;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MilestoneModifyRequest {
    @NotBlank
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate end;
}
