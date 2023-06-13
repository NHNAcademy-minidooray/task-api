package com.nhnacademy.minidooray.taskapi.repository.comment;

import com.nhnacademy.minidooray.taskapi.domain.response.CommentDto;
import com.nhnacademy.minidooray.taskapi.entity.*;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CommentRepositoryImpl extends QuerydslRepositorySupport implements CommentRepositoryCustom {
    public CommentRepositoryImpl() {
        super(Comment.class);
    }

    @Override
    public List<CommentDto> getComments(Integer projectSeq, Integer taskSeq) {
        QComment comment = QComment.comment;
        QTask task = QTask.task;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProject project = QProject.project;

        return from(comment)
                .innerJoin(comment.task , task)
                .innerJoin(comment.projectMember, projectMember)
                .innerJoin(project)
                .on(projectMember.project.projectSeq.eq(project.projectSeq))
                .where(project.projectSeq.eq(projectSeq).and(task.taskSeq.eq(taskSeq)))
                .select(Projections.constructor(CommentDto.class,
                        comment.commentSeq, comment.projectMember.projectMemberId, comment.commentContent))
                .fetch();

    }

    @Override
    public CommentDto getComment(Integer projectSeq, Integer taskSeq, Integer commentSeq) {
        QComment comment = QComment.comment;
        QTask task = QTask.task;
        QProjectMember projectMember = QProjectMember.projectMember;
        QProject project = QProject.project;

        return from(comment)
                .innerJoin(comment.task , task)
                .innerJoin(comment.projectMember, projectMember)
                .innerJoin(project)
                .on(projectMember.project.projectSeq.eq(project.projectSeq))
                .where(project.projectSeq.eq(projectSeq).and(task.taskSeq.eq(taskSeq))
                        .and(comment.commentSeq.eq(commentSeq)))
                .select(Projections.constructor(CommentDto.class,
                        comment.commentSeq, comment.projectMember.projectMemberId, comment.commentContent))
                .fetchOne();
    }

    @Override
    public List<CommentDto> getMyComments(String projectMemberId) {
        QComment comment = QComment.comment;
        QProjectMember projectMember = QProjectMember.projectMember;

        return from(comment)
                .innerJoin(comment.projectMember, projectMember)
                .where(projectMember.projectMemberId.eq(projectMemberId))
                .select(Projections.constructor(CommentDto.class,
                        comment.commentSeq, comment.projectMember.projectMemberId, comment.commentContent))
                .fetch();
    }
}
