package com.alkemy.ong.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Organization {

    private String name;
    private String image;
    private String address;
    private Integer phone;
    private String email;
    private String welcomeText;
    private String aboutUsText;
    private LocalDateTime highDate;

}
