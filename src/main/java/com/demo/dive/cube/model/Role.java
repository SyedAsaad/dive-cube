package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.UserType;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @NotNull(message = "role name cannot be null")
    @Column(
            name="role_name", unique = true
    )
    private UserType roleName;


    public Role(@NotNull(message = "role name cannot be null") UserType roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }

    public UserType getRoleName() {
        return roleName;
    }

    public void setRoleName(Integer i) {
        this.roleName = UserType.values()[i];
    }
}
