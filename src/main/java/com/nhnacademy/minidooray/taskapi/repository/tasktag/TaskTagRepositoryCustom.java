package com.nhnacademy.minidooray.taskapi.repository.tasktag;

import com.nhnacademy.minidooray.taskapi.domain.response.TaskTagDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface TaskTagRepositoryCustom {
    List<TaskTagDto> getTagsByTaskSeq(Integer taskSeq);
}
