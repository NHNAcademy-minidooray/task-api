package com.nhnacademy.minidooray.taskapi.domain.request.milestone;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class MilestoneRegisterRequest {
    @NotBlank
    private String name;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}
