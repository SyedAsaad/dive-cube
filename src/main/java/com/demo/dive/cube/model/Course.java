package com.demo.dive.cube.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course extends BaseEntity {
    @Column
    private String courseName;

    @Column
    private Integer coursePrice;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }
}
