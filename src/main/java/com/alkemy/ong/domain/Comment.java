package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Comment {
    Long idUser;
    String body;
    Long idNews;
}
