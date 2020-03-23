package com.demo.dive.cube.enums;

public enum PaymentType {
    FULL_PAYMENT("Full Payment"),
    PARTIAL_PAYMENT("Partial Payment");

    private String title;
//    public static final Map<Integer, String> keyValues = new HashMap<>();
//
//    static {
//        for(PaymentType type : PaymentType.values()){
//            keyValues.put(type.ordinal(), type.getTitle());
//        }
//    }

    PaymentType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
