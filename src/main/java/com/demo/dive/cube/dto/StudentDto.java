package com.demo.dive.cube.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.Serializable;

public class StudentDto implements Serializable {

    private Long id;
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must contain at-least 2 and at-most 50 characters")
    private String firstName;
    private String middleName;
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must contain at-least 2 and at-most 50 characters")
    private String lastName;
    private String email;
    private String permanentAddress;
    private String recievingAddress;
    @Size(min = 7, max = 20, message = "Phone Number should be at-least 7 digits and at-most 20 digits long")
    private String phoneNumber;
    private String altPhoneNumber;
    private MultipartFile image;
    private String country;
    private String state;
    @NotNull(message = "City cannot be null")
    private String city;
    private String zipCode;
    private String dob;
    @NotNull(message = "emergencyContactNum cannot be null")
    private String emergencyContactNum;
    private String emergencyContactName;
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getRecievingAddress() {
        return recievingAddress;
    }

    public void setRecievingAddress(String recievingAddress) {
        this.recievingAddress = recievingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAltPhoneNumber() {
        return altPhoneNumber;
    }

    public void setAltPhoneNumber(String altPhoneNumber) {
        this.altPhoneNumber = altPhoneNumber;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}
