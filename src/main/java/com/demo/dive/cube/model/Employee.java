package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.EmployementType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="employee")
public class Employee extends BaseEntity {
    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String telephone;
    @Column(name = "email",unique = true)
    private String email;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private Integer salaryPerWeek;
    @Column
    private String classOfPerson;
    @Column
    private EmployementType employementType;
    @Column
    private String startDate;
    @Column
    private String endDate;

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


}

