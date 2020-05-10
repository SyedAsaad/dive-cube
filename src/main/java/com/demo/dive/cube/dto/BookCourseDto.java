package com.demo.dive.cube.dto;

import com.demo.dive.cube.model.ClassRoom;
import com.demo.dive.cube.model.Course;
import com.demo.dive.cube.model.Instructor;
import com.demo.dive.cube.model.Student;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class BookCourseDto implements Serializable {

    private Long id;

    @NotNull
    private Student student;

    @NotNull
    private Course course;

    private MultipartFile claimFile;

    private ClassRoom classRoom;

    private Instructor instructor;

    @NotNull
    private String courseDate;
    @NotNull
    private String courseTime;

    private boolean isTransactionExist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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

    public Boolean getIsTransactionExist() {
        return this.isTransactionExist;
    }

    public void setIsTransactionExist(Boolean isTransactionExist) {
        this.isTransactionExist = isTransactionExist;
    }

    public MultipartFile getClaimFile() {
        return claimFile;
    }

    public void setClaimFile(MultipartFile claimFile) {
        this.claimFile = claimFile;
    }
}
