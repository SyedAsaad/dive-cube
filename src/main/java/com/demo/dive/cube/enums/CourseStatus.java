package com.demo.dive.cube.enums;

public enum CourseStatus {

    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");

    private String title;

    public String getTitle() {
        return title;
    }

    CourseStatus(String title) {
        this.title = title;
    }
}
