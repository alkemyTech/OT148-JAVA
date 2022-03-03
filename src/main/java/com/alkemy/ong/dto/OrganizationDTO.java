package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationDTO implements Serializable {
    private Long id;
    private String name;
    private String image;
    private String address;
    private Integer phone;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String welcomeText;
    @JsonIgnore
    private String aboutUsText;
    private LocalDateTime creationDate;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private List<SlideDTO> slides;
}
