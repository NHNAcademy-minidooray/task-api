package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
