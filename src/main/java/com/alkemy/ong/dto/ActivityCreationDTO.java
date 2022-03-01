package com.alkemy.ong.dto;

import com.sun.istack.NotNull;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ActivityCreationDTO implements Serializable {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String content;
    private String image;

}
