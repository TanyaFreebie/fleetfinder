package com.spring.fleetfinder.model;
//9 03

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "characters")
public class Pilot {
    @Id
    private Long charId;

    @Column(name = "char_name")
    private String charName;

    @Column(name = "total_Sp")
    private Long totalSp;

    @Column(name = "corp_id")
    private Integer corpId;

    @Column(name="corp_access")
    private Boolean corpAccess;

    @Column(name = "ally_id")
    private Integer allyId;

    @Column (name = "timezone")
    private String timeZone;

    @Column(name = "area")
    private String area;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "is_active")
    private Boolean isActive;
    //char name
    //skill points
    // title of advert Ищу пвп корпу <Посмотреть адвёрт>
}
