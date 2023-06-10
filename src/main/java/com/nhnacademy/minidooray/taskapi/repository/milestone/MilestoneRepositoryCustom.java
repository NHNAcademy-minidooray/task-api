package com.nhnacademy.minidooray.taskapi.repository.milestone;

import com.nhnacademy.minidooray.taskapi.domain.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.domain.TaskListDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface MilestoneRepositoryCustom {
    List<MilestoneDto> getMilestones(Integer projectSeq);
    Optional<MilestoneDto> getMileStone(Integer milestoneSeq);

    List<TaskListDto> getTasks(Integer projectSeq, Integer milestoneSeq);
}
