package com.nhnacademy.minidooray.taskapi.domain.request.milestone;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MilestoneRegisterRequest {
    @NotBlank(message = "name : 필수 입력값 입니다.")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}
