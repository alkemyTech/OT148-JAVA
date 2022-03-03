package com.alkemy.ong.repository.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizations")
public class OrganizationModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @OneToMany(
            mappedBy = "organizationModel",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"organizationModel"})
    private List<SlideModel> slides;


    @PrePersist
    private void beforePersisting() {
        this.creationDate = LocalDateTime.now();
    }
}
