package com.alkemy.ong.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

    @Data
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Table(name="activities")
    public class ActivityModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false)
        private String name;
        @Column(nullable = false)
        private String content;
        @Column(nullable = false)
        private String image;
        @Column(name = "creation_date")
        private LocalDateTime creationDate;

    @PrePersist
    private void beforePersisting(){
        this.creationDate = LocalDateTime.now();
    }
}

