package com.alkemy.ong.repository.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name="Activities, Campos:")
    public class ActivityModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "activities_id")
        private Long id;
        @Column
        @NotNull
        private String name;
        @NotNull
        private String text;
        @NotNull
        private String image;
        @Column(name = "created_date", updatable = false, nullable = false)
        @CreatedDate
        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private LocalDate createdDate;

        @Column(name = "modified_date")
        @LastModifiedDate
        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private LocalDate modifiedDate;

        @Column(name = "deleted_date")
        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private LocalDate deletedDate;

        @Column(name = "is_active")
        private boolean isActive = Boolean.TRUE;

        @PrePersist
    private void beforePersisting(){
        this.createdDate = LocalDate.now();
    }
}

