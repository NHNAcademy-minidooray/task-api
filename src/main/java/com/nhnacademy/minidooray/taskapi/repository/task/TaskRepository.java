package com.nhnacademy.minidooray.taskapi.repository.task;

import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer>, TaskRepositoryCustom {
    Optional<ProjectMember> findByTaskSeq(Integer taskSeq);
    Optional<Task> findByTaskSeqAndProjectMember_Project_ProjectSeq(Integer taskSeq, Integer projectSeq);
}
