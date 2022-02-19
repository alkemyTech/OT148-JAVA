package com.alkemy.ong.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Activity {

    private String name;
    private String content;
    private String image;
    private LocalDate creationDate;
    private LocalDate modifiedDate;
    private LocalDate deletedDate;
    private Boolean isActive;


}