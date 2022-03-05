package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SlideDTO implements Serializable {
    private Long id;
    private String image;
    private String text;
    private Integer order;
    private OrganizationDTO organization;
}
