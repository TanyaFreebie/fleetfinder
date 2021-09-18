package com.spring.fleetfindertest.model;
//9 03

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "test")
public class Pilot {
    @Id
    private Long charId;

    @Column(name = "char_name")
    private String charName;

    @Column(name = "char_image")
    private String charImage;// этой колонки не будет, будем генерировать сами по ID

    @Column(name = "corp_id")
    private Integer corpId;

    @Column(name = "corp_name")
    private String corpName;

    @Column(name = "ally_id")
    private Integer allyId;

    @Column(name = "ally_name")
    private String allyName;
    
    //char name
    //skill points
    // title of advert Ищу пвп корпу <Посмотреть адвёрт>
}
