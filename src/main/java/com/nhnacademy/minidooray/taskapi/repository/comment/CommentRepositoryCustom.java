package com.nhnacademy.minidooray.taskapi.repository.comment;

import com.nhnacademy.minidooray.taskapi.domain.response.CommentDto;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CommentRepositoryCustom {
    List<CommentDto> getComments(Integer projectSeq, Integer taskSeq);
    CommentDto getComment(Integer projectSeq, Integer taskSeq, Integer commentSeq);

    List<CommentDto> getMyComments(String projectMemberId);
}
