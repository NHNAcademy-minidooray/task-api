package com.nhnacademy.minidooray.taskapi.repository.task;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface TaskRepositoryCustom {
    List<TaskListDto> getProjectTasks(Integer projectSeq);
    TaskDto getTask(Integer projectSeq, Integer taskSeq);

    List<TaskListDto> getTasks(Integer projectSeq, String projectMemberId);
}
