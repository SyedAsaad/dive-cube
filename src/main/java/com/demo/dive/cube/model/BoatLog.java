package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.DiveName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "boat_log")
public class BoatLog extends BaseEntity{

    private String boatCaptain;
    @OneToOne
    private Instructor instructor;
    private String logDate;
    private String staffName;
    private DiveName diveName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "boat_log_id", nullable = false)
    private Set<BoatUserLog> boatUserLogs;

    public String getBoatCaptain() {
        return boatCaptain;
    }

    public void setBoatCaptain(String boatCaptain) {
        this.boatCaptain = boatCaptain;
    }

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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public DiveName getDiveName() {
        return diveName;
    }

    public void setDiveName(Integer i) {
        this.diveName = DiveName.values()[i];
    }

    public Set<BoatUserLog> getBoatUserLogs() {
        return boatUserLogs;
    }

    public void setBoatUserLogs(Set<BoatUserLog> boatUserLogs) {
        this.boatUserLogs = boatUserLogs;
    }

    public void addBoatUserLogs(BoatUserLog boatUserLog){
        if(boatUserLogs==null){
            boatUserLogs = new LinkedHashSet<>();
        }
        this.boatUserLogs.add(boatUserLog);
    }
    public void removeAllUserLogs(){
        if(boatUserLogs!=null)
        this.boatUserLogs.clear();
    }
}
