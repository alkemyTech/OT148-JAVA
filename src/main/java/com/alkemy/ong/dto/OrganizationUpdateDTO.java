package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationUpdateDTO {
    private String name;
    private String image;
    private String address;
    private Integer phone;
    private String email;
    private String welcomeText;
    private String aboutUsText;
}
