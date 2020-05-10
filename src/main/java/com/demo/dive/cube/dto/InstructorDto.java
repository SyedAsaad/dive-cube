package com.demo.dive.cube.dto;

import com.demo.dive.cube.config.Constants;
import com.demo.dive.cube.model.Shift;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class InstructorDto {

    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must contain at-least 2 and at-most 20 characters")
    private String instructorName;


    @NotNull(message = "Email cannot be null")
    @Pattern(regexp= Constants.EMAIL_REGEX,message="Email is not valid")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Size(min = 7, max = 20, message = "Phone Number should be at-least 7 digits and at-most 20 digits long")
    private String phoneNumber;


    @NotNull(message = "Address cannot be null")
    private String address;

    @Size(min = 7, max = 100, message = "Password should be at-least 7 digits and at-most 20 digits long")
    private String password;

    private Shift shift;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
