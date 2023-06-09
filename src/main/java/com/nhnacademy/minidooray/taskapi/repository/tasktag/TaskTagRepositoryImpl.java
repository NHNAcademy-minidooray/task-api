package com.nhnacademy.minidooray.taskapi.repository.tasktag;

import com.nhnacademy.minidooray.taskapi.domain.TaskTagDto;
import com.nhnacademy.minidooray.taskapi.entity.QTaskTag;
import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TaskTagRepositoryImpl extends QuerydslRepositorySupport implements TaskTagRepositoryCustom {
    public TaskTagRepositoryImpl() {
        super(TaskTag.class);
    }

}
