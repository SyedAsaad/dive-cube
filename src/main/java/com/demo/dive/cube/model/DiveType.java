package com.demo.dive.cube.model;

import com.fasterxml.jackson.databind.ser.Serializers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "dive_type")
public class DiveType extends BaseEntity {

    @Column(name = "dive_name",unique = true)
    @NotNull(message = "Dive Name cannot be null")
    @Size(min = 2, max = 50, message = "Dive Name must contain at-least 2 and at-most 20 characters")
    private String diveName;

    @NotNull(message = "Amount cannot be null")
    @Min(1)
    private Double amount;

    public String getDiveName() {
        return diveName;
    }

    public void setDiveName(String diveName) {
        this.diveName = diveName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
