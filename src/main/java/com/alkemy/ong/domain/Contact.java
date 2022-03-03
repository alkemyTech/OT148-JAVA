package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contact {
    private String name;
    private Integer phone;
    private String email;
    private String message;
}
