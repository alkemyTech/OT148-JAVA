package com.alkemy.ong.repository.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizations")
public class OrganizationModel implements Serializable {

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


    @PrePersist
    private void beforePersisting(){
        this.creationDate = LocalDateTime.now();
    }

}
