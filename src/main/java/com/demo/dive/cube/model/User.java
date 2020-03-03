package com.demo.dive.cube.model;

import com.demo.dive.cube.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Table
@Entity(name = "user")

public class User extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 50, message = "First Name must contain at-least 2 and at-most 20 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 50, message = "Last Name must contain at-least 2 and at-most 20 characters")
    private String lastName;

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
    @NotNull(message = "Password number cannot be null")
    @Size(min = 5, max = 200, message = "Password should be at-least 7 digits and at-most 20 digits long")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsEnabled() {
        return this.isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
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
}
