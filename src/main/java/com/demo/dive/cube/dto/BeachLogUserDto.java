package com.demo.dive.cube.dto;

import com.demo.dive.cube.enums.CertificationLevel;

public class BeachLogUserDto {
    private Long id;
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

    public void setCertificationLevel(CertificationLevel certificationLevel) {
        this.certificationLevel = certificationLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
