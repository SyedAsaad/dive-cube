package com.demo.dive.cube.dto;

import com.demo.dive.cube.enums.CertificationLevel;

public class BoatLogUserDto {
    private Long id;
    private String name;
    private CertificationLevel certificationLevel;
    private Integer numDives;
    private String gide;
    private String checkInSite1;
    private String checkInSite2;
    private Integer numDivers;
    private String lastDiveDate;

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

    public Integer getNumDives() {
        return numDives;
    }

    public void setNumDives(Integer numDives) {
        this.numDives = numDives;
    }

    public String getGide() {
        return gide;
    }

    public void setGide(String gide) {
        this.gide = gide;
    }

    public String getCheckInSite1() {
        return checkInSite1;
    }

    public void setCheckInSite1(String checkInSite1) {
        this.checkInSite1 = checkInSite1;
    }

    public String getCheckInSite2() {
        return checkInSite2;
    }

    public void setCheckInSite2(String checkInSite2) {
        this.checkInSite2 = checkInSite2;
    }

    public Integer getNumDivers() {
        return numDivers;
    }

    public void setNumDivers(Integer numDivers) {
        this.numDivers = numDivers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastDiveDate() {
        return lastDiveDate;
    }

    public void setLastDiveDate(String lastDiveDate) {
        this.lastDiveDate = lastDiveDate;
    }
}
