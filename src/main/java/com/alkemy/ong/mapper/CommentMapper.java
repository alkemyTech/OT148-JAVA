package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.repository.model.CommentModel;

public class CommentMapper {
    public static Comment mapModelToDomain(CommentModel commentModel) {
        Comment comment = Comment.builder()
                .id(commentModel.getId())
                .user_id(commentModel.getUser_id())
                .body(commentModel.getBody())
                .news_id(commentModel.getNews_id())
                .creationDate(commentModel.getCreationDate())
                .build();
        return comment;
    }

    public static CommentModel mapDomainToModel(Comment comment) {
        CommentModel commentModel = CommentModel.builder()
                .id(comment.getId())
                .user_id(comment.getUser_id())
                .body(comment.getBody())
                .news_id(comment.getNews_id())
                .creationDate(comment.getCreationDate())
                .build();
        return commentModel;
    }

    public static CommentDTO mapDomainToDto(Comment comment) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .user_id(comment.getUser_id())
                .body(comment.getBody())
                .news_id(comment.getNews_id())
                .creationDate(comment.getCreationDate())
                .build();
        return commentDTO;
    }

    public static Comment mapDtoToDomain(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .id(commentDTO.getId())
                .user_id(commentDTO.getUser_id())
                .body(commentDTO.getBody())
                .news_id(commentDTO.getNews_id())
                .creationDate(commentDTO.getCreationDate())
                .build();
        return comment;
    }
}
