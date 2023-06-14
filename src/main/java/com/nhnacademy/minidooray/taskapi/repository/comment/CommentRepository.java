package com.nhnacademy.minidooray.taskapi.repository.comment;

import com.nhnacademy.minidooray.taskapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Integer>, CommentRepositoryCustom {
    Optional<Comment> findByCommentSeqAndTask_TaskSeq(Integer commentSeq, Integer taskSeq);

}
