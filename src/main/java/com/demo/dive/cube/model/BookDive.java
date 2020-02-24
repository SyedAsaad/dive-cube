package com.demo.dive.cube.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Table(name = "book_dive")
public class BookDive extends BaseEntity {

    private String name;
    private String address;
    private String contactNumber;
    @NotNull
    private String tripDate;
    private String tripTime;
    @OneToOne
    private DiveType diveType;
    private Double depositAmount;

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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public DiveType getDiveType() {
        return diveType;
    }

    public void setDiveType(DiveType diveType) {
        this.diveType = diveType;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }
}
