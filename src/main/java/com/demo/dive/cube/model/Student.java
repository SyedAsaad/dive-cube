package com.demo.dive.cube.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "course_student")
public class Student extends BaseEntity {

    private String studentId;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must contain at-least 2 and at-most 50 characters")
    private String name;
    private String permanentAddress;

    @Column(name="phone_number",length = 30)
    @NotNull
    @Size(min = 7, max = 20, message = "Phone Number should be at-least 7 digits and at-most 20 digits long")
    private String phoneNumber;

    private String country;
    private String city;
    private String zipCode;
    @Column(name = "date_of_birth")
    private String dob;
    @Column(name = "emergency_contact_no")
    @NotNull(message = "emergencyContactNum cannot be null")
    private String emergencyContactNum;

    @NotNull(message = "emergencyContactName cannot be null")
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;
    private String gender;
    private String recievingAddress;
    private String imageName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmergencyContactNum() {
        return emergencyContactNum;
    }

    public void setEmergencyContactNum(String emergencyContactNum) {
        this.emergencyContactNum = emergencyContactNum;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRecievingAddress() {
        return recievingAddress;
    }

    public void setRecievingAddress(String recievingAddress) {
        this.recievingAddress = recievingAddress;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
