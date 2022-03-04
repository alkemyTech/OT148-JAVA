package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.repository.model.CommentModel;

public class CommentMapper {
    public static Comment mapModelToDomain(CommentModel commentModel) {
        Comment comment = Comment.builder()
                .id(commentModel.getId())
                .idUser(commentModel.getIdUser())
                .body(commentModel.getBody())
                .idNews(commentModel.getIdNews())
                .creationDate(commentModel.getCreationDate())
                .build();
        return comment;
    }

    public static CommentModel mapDomainToModel(Comment comment) {
        CommentModel commentModel = CommentModel.builder()
                .id(comment.getId())
                .idUser(comment.getIdUser())
                .body(comment.getBody())
                .creationDate(comment.getCreationDate())
                .build();
        return commentModel;
    }
}
