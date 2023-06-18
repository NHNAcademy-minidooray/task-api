package com.nhnacademy.minidooray.taskapi.domain.request.comment;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRegisterRequest {
    @NotBlank(message = "content : 필수 입력값 입니다.")
    private String content;
}
