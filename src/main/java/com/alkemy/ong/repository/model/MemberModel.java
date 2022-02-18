package com.alkemy.ong.repository.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name="members")
public class MemberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_member",nullable = false)
    private Integer idMember;
    @Column(nullable = false)
    private String name;
    @Column(name="facebook_url", nullable = true)
    private String facebookUrl;
    @Column(name="instagram_url", nullable = true)
    private String instagramUrl;
    @Column(name="linkedin_url", nullable = true )
    private String linkedinUrl;
    @Column(nullable = false)
    private String image;
    @Column(nullable = true)
    private String description;
}
