package com.demo.dive.cube.enums;

import java.util.HashMap;
import java.util.Map;

public enum EmployementType {
    Sub_Contract("Sub Contract"),
    Employee("Employee");

    EmployementType(String title){
        this.title = title;
    }

    private String title;
}
