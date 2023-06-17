package com.nhnacademy.minidooray.taskapi.domain.request.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class TagModifyRequest {
    @NotBlank(message = "name : 필수 입력값 입니다.")
    private String name;
}
