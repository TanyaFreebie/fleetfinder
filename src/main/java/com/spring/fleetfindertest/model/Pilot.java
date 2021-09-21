package com.spring.fleetfindertest.model;
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

    @Column(name = "corp_id")
    private Integer corpId;

//    @Column(name = "char_image")
//    private String charImage;

//    @Column(name = "corp_role")
//    private String corpRole;
    @Column(name="corp_access")
    private String corpAccess;

    @Column(name = "ally_id")
    private Integer allyId;

//    @Column(name = "ally_name")
//    private String allyName;

    @Column(name = "total_Sp")
    private Long totalSp;
    //char name
    //skill points
    // title of advert Ищу пвп корпу <Посмотреть адвёрт>
}
