package com.nhnacademy.minidooray.taskapi.repository.task;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface TaskRepositoryCustom {
    List<TaskListDto> getProjectTasks(Integer projectSeq);
    TaskDto getTask(Integer projectSeq, Integer taskSeq);

    List<TaskListDto> getTasks(Integer projectSeq, String projectMemberId);

    ProjectMember getWriter(Integer taskSeq);

}
