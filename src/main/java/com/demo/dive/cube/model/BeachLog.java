package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.DiveName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "beach_log")
public class BeachLog extends BaseEntity{

    @OneToOne
    private Instructor instructor;
    private String crew;
    private String logDate;
    private String activities;
    private DiveName diveName;
    private String site;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "beach_log_id", nullable = false)
    private Set<BeachUserLog> beachUserLogs;


    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }


    public void addBoatUserLogs(BeachUserLog beachUserLog){
        if(beachUserLogs==null){
            beachUserLogs = new LinkedHashSet<>();}
        this.beachUserLogs.add(beachUserLog);
    }
    public void removeAllUserLogs(){
        if(beachUserLogs!=null)
        this.beachUserLogs.clear();
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public DiveName getDiveName() {
        return diveName;
    }

    public void setDiveName(Integer i) {
        this.diveName = DiveName.values()[i];
    }

    public Set<BeachUserLog> getBeachUserLogs() {
        return beachUserLogs;
    }

    public void setBeachUserLogs(Set<BeachUserLog> beachUserLogs) {
        this.beachUserLogs = beachUserLogs;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
