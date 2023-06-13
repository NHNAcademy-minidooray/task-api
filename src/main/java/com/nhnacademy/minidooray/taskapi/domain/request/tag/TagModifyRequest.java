package com.nhnacademy.minidooray.taskapi.domain.request.tag;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TagModifyRequest {
    @NotBlank
    private String name;
}
