package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.dto.CommentDTO;
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
                .idNews(comment.getIdNews())
                .creationDate(comment.getCreationDate())
                .build();
        return commentModel;
    }

    public static CommentDTO mapDomainToDto(Comment comment) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .idUser(comment.getIdUser())
                .body(comment.getBody())
                .idNews(comment.getIdNews())
                .creationDate(comment.getCreationDate())
                .build();
        return commentDTO;
    }

    public static Comment mapDtoToDomain(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .id(commentDTO.getId())
                .idUser(commentDTO.getId())
                .body(commentDTO.getBody())
                .idNews(commentDTO.getIdNews())
                .creationDate(commentDTO.getCreationDate())
                .build();
        return comment;
    }
}
