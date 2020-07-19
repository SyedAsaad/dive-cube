package com.demo.dive.cube.dto;

public class CountryDto {

    private String name;
    private String dialCode;
    private String code;

    public CountryDto(String name, String dialCode, String code) {
        this.name = name;
        this.dialCode = dialCode;
        this.code = code;
    }

    public CountryDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }
}
