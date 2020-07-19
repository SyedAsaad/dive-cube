package com.demo.dive.cube.dto;

import com.demo.dive.cube.enums.EmployementType;
import com.demo.dive.cube.model.Shift;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class EmployeeDto {

    private Long id;

    private String name;

    private String address;

    private String telephone;

    private String email;

    private String city;

    private String country;

    private Integer salaryPerWeek;

    private String classOfPerson;

    private EmployementType employementType;

    private String startDate;

    private String endDate;

    @Size(min = 7, max = 100, message = "Password should be at-least 7 digits and at-most 20 digits long")
    private String password;

    private Shift shift;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSalaryPerWeek() {
        return salaryPerWeek;
    }

    public void setSalaryPerWeek(Integer salaryPerWeek) {
        this.salaryPerWeek = salaryPerWeek;
    }

    public String getClassOfPerson() {
        return classOfPerson;
    }

    public void setClassOfPerson(String classOfPerson) {
        this.classOfPerson = classOfPerson;
    }

    public EmployementType getEmployementType() {
        return employementType;
    }

    public void setEmployementType(EmployementType employementType) {
        this.employementType = employementType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
