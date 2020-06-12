package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.CertificationLevel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "boat_user_log")
public class BoatUserLog extends BaseEntity {

    private String name;
    private CertificationLevel certificationLevel;
    private Integer numDives;
    private String gide;
    private String checkInSite1;
    private String checkInSite2;
    private Integer numDivers;
    private String lastDiveDate;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name="BoatLogId", referencedColumnName="id", nullable = true)
//    private BoatLog boatLog;

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

    public void setLastDiveDate(String lastDiveDate) {
        this.lastDiveDate = lastDiveDate;
    }

    public String getLastDiveDate() {
        return lastDiveDate;
    }
}
