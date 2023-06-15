package com.nhnacademy.minidooray.taskapi.domain.request.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentModifyRequest {

    @NotBlank(message = "content : 필수 입력값 입니다.")
    private String content;
}
