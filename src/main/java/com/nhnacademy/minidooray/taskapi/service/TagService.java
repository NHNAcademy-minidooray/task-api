package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.response.TagDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.domain.request.tag.TagModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.tag.TagRegisterRequest;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public List<TagDto> getTags(Integer projectSeq) {
        if (!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트입니다.");
        }
        return tagRepository.getTags(projectSeq);
    }

    public List<TaskListDto> getTasks(Integer projectSeq, Integer tagSeq) {
        if (!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트입니다.");
        }

        if (Objects.isNull(tagRepository.getTag(tagSeq))) {
            throw new NotFoundException("해당 프로젝트에 등록되지 않은 태그입니다.");
        }

        return tagRepository.getTasks(projectSeq, tagSeq);
    }


    //todo 보류
    public TagDto getTag(Integer projectSeq, Integer tagSeq) {
        if (!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트입니다.");
        }

        TagDto tagDto = tagRepository.getTag(tagSeq);

        if (Objects.isNull(tagDto)) {
            throw new NotFoundException("해당 프로젝트에 등록되지 않은 태그입니다.");
        }

        return tagDto;
    }

    @Transactional
    public TagDto createTag(TagRegisterRequest request, Integer projectSeq) {
        Project project = projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로젝트입니다."));

        Tag tag = Tag.builder()
                .tagName(request.getName())
                .project(project)
                .build();

        tagRepository.save(tag);

        return tagRepository.getTag(tag.getTagSeq());
    }

    @Transactional
    public TagDto updateTag(TagModifyRequest request, Integer projectSeq, Integer tagSeq) {
        if (!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트입니다.");
        }
        Tag tag = tagRepository.findById(tagSeq).orElseThrow(
                () -> new NotFoundException("등록되지 않은 태그입니다."));

        if (Objects.isNull(tagRepository.getTag(tagSeq))) {
            throw new NotFoundException("해당 프로젝트에 등록되지 않은 태그입니다.");
        }
        tag.update(request.getName());


        return tagRepository.getTag(tag.getTagSeq());
    }

    @Transactional
    public void deleteTag(Integer projectSeq, Integer tagSeq) {
        if (!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트입니다.");
        }
        Tag tag = tagRepository.findById(tagSeq).orElseThrow(
                () -> new NotFoundException("등록되지 않은 태그입니다."));

        if (Objects.isNull(tagRepository.getTag(tagSeq))) {
            throw new NotFoundException("해당 프로젝트에 등록되지 않은 태그입니다.");
        }
        tagRepository.delete(tag);
    }


}
