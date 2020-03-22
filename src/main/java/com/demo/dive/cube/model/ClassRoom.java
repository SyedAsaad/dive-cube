package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.ClassSection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="class_room")
public class ClassRoom extends BaseEntity{
    @Column
    private String room;

    @Column
    private Integer studentCapacity;

    @Column
    private ClassSection classSection;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getStudentCapacity() {
        return studentCapacity;
    }

    public void setStudentCapacity(Integer studentCapacity) {
        this.studentCapacity = studentCapacity;
    }

    public ClassSection getClassSection() {
        return classSection;
    }

    public void setClassSection(ClassSection classSection) {
        this.classSection = classSection;
    }
}
