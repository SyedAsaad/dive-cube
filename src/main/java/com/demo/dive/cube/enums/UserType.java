package com.demo.dive.cube.enums;

public enum UserType {

    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    INSTRUCTOR("Instructor");

    private String title;
//    public static final Map<Integer, String> keyValues = new HashMap<>();
//
//    static {
//        for(PaymentType type : PaymentType.values()){
//            keyValues.put(type.ordinal(), type.getTitle());
//        }
//    }

    UserType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
