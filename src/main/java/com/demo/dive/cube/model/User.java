package com.demo.dive.cube.model;

import com.demo.dive.cube.config.Constants;
import com.demo.dive.cube.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity(name = "user")
public class User extends BaseEntity{

    @Column(name = "name", nullable = false)
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "First Name must contain at-least 2 and at-most 20 characters")
    private String name;

    @Column(name = "email", nullable = false,unique = true)
    @NotNull(message = "Email cannot be null")
    @Pattern(regexp= Constants.EMAIL_REGEX,message="Email is not valid")
    private String email;

    @Column(name="phone_number",length = 30)
    @NotNull(message = "Phone number cannot be null")
    @Size(min = 7, max = 20, message = "Phone Number should be at-least 7 digits and at-most 20 digits long")
    private String phoneNumber;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "address")
    @NotNull(message = "Address cannot be null")
    private String address;

    @Column(name="password",length = 200)
//    @NotNull(message = "Password number cannot be null")
    @Size(min = 5, max = 200, message = "Password should be at-least 7 digits and at-most 20 digits long")
    private String password;

    @OneToOne()
    private Role role ;

    @OneToOne
    private Shift shift;

    public User(String name, String email,String address, String phoneNumber, Boolean isEnabled, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isEnabled = isEnabled;
        this.address = address;
        this.password = password;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
