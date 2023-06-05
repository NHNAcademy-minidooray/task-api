package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.entity.StatusCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusCodeRepository extends JpaRepository<StatusCode, Integer> {
    StatusCode findByStatusCodeName(String statusCodeName);
}
