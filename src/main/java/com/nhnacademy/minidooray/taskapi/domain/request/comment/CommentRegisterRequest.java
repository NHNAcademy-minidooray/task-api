package com.nhnacademy.minidooray.taskapi.domain.request.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CommentRegisterRequest {

    @NotBlank
    private String content;
}