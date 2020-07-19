package com.demo.dive.cube.dto;

import com.demo.dive.cube.enums.DiveName;
import com.demo.dive.cube.model.Instructor;

import java.util.ArrayList;
import java.util.List;

public class BoatLogDto {

    private Long id;
    private String boatCaptain;
    private Instructor instructor;
    private String logDate;
    private String staffName;
    private DiveName diveName;
    private List<BoatLogUserDto> boatLogUserDtoList = new ArrayList<>();

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

    public List<BoatLogUserDto> getBoatLogUserDtoList() {
        return boatLogUserDtoList;
    }

    public void setBoatLogUserDtoList(List<BoatLogUserDto> boatLogUserDtoList) {
        this.boatLogUserDtoList = boatLogUserDtoList;
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

    public void addBoatUserLogs(BoatLogUserDto boatLogUserDto){
        if(boatLogUserDtoList==null){
            boatLogUserDtoList = new ArrayList<>();
        }
        boatLogUserDtoList.add(boatLogUserDto);
    }
}
