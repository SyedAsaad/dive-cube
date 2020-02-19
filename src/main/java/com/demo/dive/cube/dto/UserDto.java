package com.demo.dive.cube.dto;

import com.demo.dive.cube.config.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
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


    @Column(name = "address")
    @NotNull(message = "Address cannot be null")
    private String address;

    @Column(name="password",length = 30)
    @NotNull(message = "Password  cannot be null")
    @Size(min = 7, max = 20, message = "Password should be at-least 7 digits and at-most 20 digits long")
    private String password;

}
