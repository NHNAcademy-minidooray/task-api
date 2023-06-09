package com.nhnacademy.minidooray.taskapi.domain.request.task;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskModifyRequest {
    private String title;
    private String content;
    private String milestoneName;
    private List<String> tagNames = new ArrayList<>();
}
