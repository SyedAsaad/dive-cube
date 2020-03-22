package com.demo.dive.cube.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book_course")
public class BookCourse extends BaseEntity {

    private Student student;

    private Course course;

    @NotNull
    private String tripDate;
    @NotNull
    private String tripTime;
}
