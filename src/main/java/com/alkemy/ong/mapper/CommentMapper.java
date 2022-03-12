package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Comment;
import com.alkemy.ong.dto.CommentBodyDTO;
import com.alkemy.ong.dto.CommentCreationDTO;
import com.alkemy.ong.dto.CommentDTO;
import com.alkemy.ong.repository.model.CommentModel;

public class CommentMapper {
    public static Comment mapModelToDomain(CommentModel commentModel) {
        Comment comment = Comment.builder()
                .id(commentModel.getId())
                .userId(commentModel.getUserId())
                .body(commentModel.getBody())
                .newsId(commentModel.getNewsId())
                .creationDate(commentModel.getCreationDate())
                .build();
        return comment;
    }

    public static CommentModel mapDomainToModel(Comment comment) {
        CommentModel commentModel = CommentModel.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .body(comment.getBody())
                .newsId(comment.getNewsId())
                .creationDate(comment.getCreationDate())
                .build();
        return commentModel;
    }

    public static CommentDTO mapDomainToDto(Comment comment) {
        CommentDTO commentDTO = CommentDTO.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .body(comment.getBody())
                .newsId(comment.getNewsId())
                .creationDate(comment.getCreationDate())
                .build();
        return commentDTO;
    }

    public static Comment mapDtoToDomain(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .id(commentDTO.getId())
                .userId(commentDTO.getUserId())
                .body(commentDTO.getBody())
                .newsId(commentDTO.getNewsId())
                .creationDate(commentDTO.getCreationDate())
                .build();
        return comment;
    }

    public static CommentBodyDTO mapDomainToBodyDTO(Comment comment) {
        CommentBodyDTO commentBodyDTO = CommentBodyDTO.builder()
                .body(comment.getBody())
                .build();
        return commentBodyDTO;
    }

    public static Comment mapCreationDTOToDomain(CommentCreationDTO commentCreationDTO) {
        Comment comment = Comment.builder()
                .body(commentCreationDTO.getBody())
                .newsId(commentCreationDTO.getNewsId())
                .build();
        return comment;
    }

    public static CommentModel mapDomainCreationToModel(Comment comment) {
        CommentModel commentModel = CommentModel.builder()
                .body(comment.getBody())
                .newsId(comment.getNewsId())
                .userId(comment.getUserId())
                .build();
        return commentModel;
    }

}
