package com.spring.fleetfindertest.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "corporations")
public class Corporation {
    @Id
    private Long corpId;

    @Column(name = "corp_name")
    private String corpName;

    @Column(name = "corp_ticker")
    private String corpTicker;

    @Column(name = "member_count")
    private Long memberCount;

    @Column(name = "ally_id")
    private Long allyId;

    @Column (name = "timezone")
    private String timeZone;

    @Column(name = "area")
    private String area;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "is_active")
    private Boolean isActive;

}
