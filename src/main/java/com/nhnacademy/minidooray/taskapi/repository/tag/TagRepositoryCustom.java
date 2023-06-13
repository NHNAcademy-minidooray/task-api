package com.nhnacademy.minidooray.taskapi.repository.tag;

import com.nhnacademy.minidooray.taskapi.domain.response.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface TagRepositoryCustom {
    List<TagDto> getTags(Integer projectSeq);
    List<TaskListDto> getTasks(Integer projectSeq, Integer tagSeq);
    TagDto getTag(Integer tagSeq);
}
