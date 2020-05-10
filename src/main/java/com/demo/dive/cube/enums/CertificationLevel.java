package com.demo.dive.cube.enums;

public enum CertificationLevel {

    PICK_OF_WATER_DIVER("Pick Ow water diver"),
    JR_OPEN_WATER_DIVER("Jr.Open water Diver"),
    RESCUE_DIVER("Rescue Diver"),
    DIVE_MASTER("Dive Master");

    private String title;

    public String getTitle() {
        return title;
    }

    CertificationLevel(String title) {
        this.title = title;
    }
    
}
