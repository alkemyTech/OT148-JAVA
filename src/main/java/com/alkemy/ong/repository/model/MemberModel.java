package com.alkemy.ong.repository.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Data
@Builder
@Entity
@Table(name="members")
public class MemberModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_member",nullable = false)
    private Long idMember;
    @Column(nullable = false)
    private String name;
    @Column(name="facebook_url")
    private String facebookUrl;
    @Column(name="instagram_url")
    private String instagramUrl;
    @Column(name="linkedin_url")
    private String linkedinUrl;
    @Column(nullable = false)
    private String image;
    @Column
    private String description;

    public MemberModel(Long idMember, String name, String facebookUrl, String instagramUrl, String linkedinUrl, String image, String description) {
        this.idMember = idMember;
        this.name = name;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.image = image;
        this.description = description;
    }

    public MemberModel(String name, String facebookUrl, String instagramUrl, String linkedinUrl, String image, String description) {
        this.name = name;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.image = image;
        this.description = description;
    }

    public MemberModel() {}
}
