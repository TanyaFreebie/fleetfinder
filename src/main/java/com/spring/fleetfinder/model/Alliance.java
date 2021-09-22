package com.spring.fleetfinder.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "alliances")
public class Alliance {
    @Id
    private Long allyId;

    @Column(name = "ally_name")
    private String allyName;

    @Column(name = "ally_ticker")
    private String allyTicker;

}
