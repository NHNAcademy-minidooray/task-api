package com.nhnacademy.minidooray.taskapi.repository.tag;

import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer>, TagRepositoryCustom {
    Boolean existsByTagName(String tagName);
    Optional<Tag> findByTagName(String tagName);
}
