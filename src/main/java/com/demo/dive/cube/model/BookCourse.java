package com.demo.dive.cube.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book_course")
public class BookCourse extends BaseEntity {

    private String bookingId;

    @NotNull
    @ManyToOne
    private Student student;

    @NotNull
    @ManyToOne
    private Course course;

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    private ClassRoom classRoom;

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    private Instructor instructor;

    @NotNull
    private String courseDate;
    @NotNull
    private String courseTime;


    private boolean isTransactionExist;


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Boolean getIsTransactionExist() {
        return this.isTransactionExist;
    }

    public void setIsTransactionExist(Boolean isTransactionExist) {
        this.isTransactionExist = isTransactionExist;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
