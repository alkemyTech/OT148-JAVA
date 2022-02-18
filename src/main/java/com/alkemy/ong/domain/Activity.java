package com.alkemy.ong.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Activity {

    private String name;
    private String content;
    private String image;
    private LocalDateTime creationDate;

}