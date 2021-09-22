package com.spring.fleetfinder.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "advertisement")
public class Advertisement {
    @Id
    private Long authorId;

    @Column (name = "timezone")
    private String timeZone;

    @Column(name = "area")
    private String area;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "advert_text")
    private String advertText;

    @Column(name = "is_active")
    private Boolean isActive;
}
