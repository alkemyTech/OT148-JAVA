package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityUpdateDTO implements Serializable {
    private String name;
    private String content;
    private String image;
}
