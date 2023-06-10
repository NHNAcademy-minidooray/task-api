package com.nhnacademy.minidooray.taskapi.repository.tag;

import com.nhnacademy.minidooray.taskapi.domain.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskListDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface TagRepositoryCustom {
    List<TagDto> getTags(Integer projectSeq);
    List<TaskListDto> getTasks(Integer projectSeq, Integer tagSeq);
    Optional<TagDto> getTag(Integer tagSeq);
}
