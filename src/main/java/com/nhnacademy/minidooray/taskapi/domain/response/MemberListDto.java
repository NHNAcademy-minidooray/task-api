package com.nhnacademy.minidooray.taskapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberListDto {
    @JsonProperty("member-id")
    private Integer projectMemberSeq;
}
