package com.alkemy.ong.repository.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "organizations")
public class OrganizationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;
    @Column(nullable = true)
    private String address;
    @Column(nullable = true)
    private Integer phone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String welcomeText;
    @Column(nullable = true)
    private String aboutUsText;
    @Column(name = "high_date")
    private LocalDateTime highDate;

    public OrganizationModel(String name,
                             String image,
                             String address,
                             Integer phone,
                             String email,
                             String welcomeText,
                             String aboutUsText) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.welcomeText = welcomeText;
        this.aboutUsText = aboutUsText;
    }

    public OrganizationModel(){}

    @PrePersist
    private void beforePersisting(){
        this.highDate = LocalDateTime.now();
    }

}
