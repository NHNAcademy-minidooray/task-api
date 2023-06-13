package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.response.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.domain.response.TaskListDto;
import com.nhnacademy.minidooray.taskapi.domain.request.milestone.MilestoneModifyRequest;
import com.nhnacademy.minidooray.taskapi.domain.request.milestone.MilestoneRegisterRequest;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.repository.milestone.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    public List<MilestoneDto> getMilestones(Integer projectSeq) {
        if(!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트 입니다.");
        }

        return milestoneRepository.getMilestones(projectSeq);
    }

    public MilestoneDto getMilestone(Integer projectSeq, Integer milestoneSeq) {
        if(!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트 입니다.");
        }

       MilestoneDto milestoneDto =  milestoneRepository.getMileStone(milestoneSeq).orElseThrow(
               () -> new NotFoundException("해당 프로젝트에 등록되지 않은 마일스톤입니다.")
       );
        return milestoneDto;
    }

    public List<TaskListDto> getTasks(Integer projectSeq, Integer milestoneSeq) {
        if(!projectRepository.existsById(projectSeq)) {
            throw new NotFoundException("등록되지 않은 프로젝트 입니다.");
        }

        milestoneRepository.getMileStone(milestoneSeq).orElseThrow(
                () -> new NotFoundException("해당 프로젝트에 등록되지 않은 마일스톤입니다.")
        );

        return milestoneRepository.getTasks(projectSeq, milestoneSeq);
    }

    @Transactional
    public MilestoneDto createMilestone(MilestoneRegisterRequest registerRequest, Integer projectSeq) {
        Project project = projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("등록되지 않은 프로젝트 입니다."));

        Milestone milestone = Milestone.builder()
                .milestoneName(registerRequest.getName())
                .milestoneStartPeriod(registerRequest.getStart())
                .milestoneEndOfPeriod(registerRequest.getEnd())
                .project(project)
                .build();

        milestoneRepository.save(milestone);

        return milestoneRepository.getMileStone(milestone.getMilestoneSeq())
                .orElseThrow(() -> new NotFoundException("해당 마일스톤을 찾을 수 없습니다."));
    }

    @Transactional
    public MilestoneDto modifyMilestone(MilestoneModifyRequest modifyRequest, Integer projectSeq, Integer milestoneSeq) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("등록되지 않은 프로젝트 입니다."));

        Milestone milestone = milestoneRepository.findById(milestoneSeq).orElseThrow(
                () -> new NotFoundException("등록되지 않은 마일스톤 입니다."));

        milestoneRepository.getMileStone(milestoneSeq).orElseThrow(
                () -> new NotFoundException("해당 프로젝트데 등록되지 않은 마일스톤 입니다."));

        milestone.update(modifyRequest.getName(), modifyRequest.getStart(), modifyRequest.getEnd());

        return milestoneRepository.getMileStone(milestone.getMilestoneSeq()).get();
    }

    @Transactional
    public void deleteMilestone(Integer projectSeq, Integer milestoneSeq) {
        projectRepository.findById(projectSeq).orElseThrow(
                () -> new NotFoundException("등록되지 않은 프로젝트 입니다."));

        Milestone milestone = milestoneRepository.findById(milestoneSeq).orElseThrow(
                () -> new NotFoundException("등록되지 않은 마일스톤 입니다."));

        milestoneRepository.getMileStone(milestoneSeq).orElseThrow(
                () -> new NotFoundException("해당 프로젝트데 등록되지 않은 마일스톤 입니다."));

        milestoneRepository.delete(milestone);
    }

}
