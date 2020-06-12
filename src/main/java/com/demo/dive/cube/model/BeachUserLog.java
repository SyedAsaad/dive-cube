package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.CertificationLevel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "beach_user_log")
public class BeachUserLog extends BaseEntity {

    private String name;
    private CertificationLevel certificationLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CertificationLevel getCertificationLevel() {
        return certificationLevel;
    }

    public void setCertificationLevel(Integer i) {
        this.certificationLevel = CertificationLevel.values()[i];
    }



}
