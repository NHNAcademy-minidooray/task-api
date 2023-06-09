package com.nhnacademy.minidooray.taskapi.repository.project;

import com.nhnacademy.minidooray.taskapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer>, ProjectRepositoryCustom {

}
