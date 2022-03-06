package com.alkemy.ong.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SlideCreationDTO implements Serializable {

    private String image;
    private String text;
    private Integer order;
    private Long organizationId;
}
