package com.demo.dive.cube.dto;

import com.demo.dive.cube.enums.DiveName;
import com.demo.dive.cube.model.Instructor;

import java.util.ArrayList;
import java.util.List;

public class BeachLogDto {

    private Long id;
    private String crew;
    private Instructor instructor;
    private String logDate;
    private String activities;
    private String site;
    private DiveName diveName;
    private List<BeachLogUserDto> beachLogUserDtos = new ArrayList<>();

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

    public DiveName getDiveName() {
        return diveName;
    }

    public void setDiveName(DiveName diveName) {
        this.diveName = diveName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<BeachLogUserDto> getBeachLogUserDtos() {
        return beachLogUserDtos;
    }

    public void setBeachLogUserDtos(List<BeachLogUserDto> beachLogUserDtos) {
        this.beachLogUserDtos = beachLogUserDtos;
    }

    public void addBeachUserLogs(BeachLogUserDto beachLogUserDto){
        if(beachLogUserDtos==null){
            beachLogUserDtos = new ArrayList<>();
        }
        beachLogUserDtos.add(beachLogUserDto);
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
