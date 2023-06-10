package com.nhnacademy.minidooray.taskapi.repository.milestone;

import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MilestoneRepository extends JpaRepository<Milestone, Integer>, MilestoneRepositoryCustom {
    Optional<Milestone> findByMilestoneName(String milestoneName);
}
