package com.alkemy.ong.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentCreationDTO implements Serializable {

    @NotNull
    private Long userId;
    @NotNull
    @NotBlank
    private String body;
    @NotNull
    private Long newsId;

}