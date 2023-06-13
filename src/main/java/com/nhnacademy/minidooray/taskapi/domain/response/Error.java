package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 *   "timestamp": "2023-06-06T12:08:38.929+00:00",
 *   "status": 500,
 *   "error": "Internal Server Error",
 *   "path": "/projects/3"
 */

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime timeStamp;
    Integer status;
    String error;
    String path;
}
