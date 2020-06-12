package com.demo.dive.cube.enums;

public enum DiveName {

    AM("AM"),
    PM("PM"),
    NIGHT("Night"),
    CHARTER("Charter");


    private String title;

    DiveName(String title) {
        this.title = title;
    }
}
