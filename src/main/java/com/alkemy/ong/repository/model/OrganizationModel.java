package com.alkemy.ong.repository.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Data
@Builder
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
    @Column()
    private String address;
    @Column()
    private Integer phone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String welcomeText;
    @Column()
    private String aboutUsText;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

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
        this.creationDate = LocalDateTime.now();
    }

}
