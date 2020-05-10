package com.demo.dive.cube.model;

import com.fasterxml.jackson.databind.ser.Serializers;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @NotNull(message = "role name cannot be null")
    private String roleName;
}
