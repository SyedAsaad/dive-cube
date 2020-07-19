package com.demo.dive.cube.enums;

public enum CertificationType {

    CARD("Card"),
    E_CARD("E-Card");

    private String title;


    CertificationType(String title) {
        this.title = title;
    }
}
