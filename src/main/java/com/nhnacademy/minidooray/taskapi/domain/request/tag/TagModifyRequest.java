package com.nhnacademy.minidooray.taskapi.domain.request.tag;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagModifyRequest {
    @NotBlank(message = "name : 필수 입력값 입니다.")
    private String name;
}
