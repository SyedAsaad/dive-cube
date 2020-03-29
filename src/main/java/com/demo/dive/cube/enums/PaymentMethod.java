package com.demo.dive.cube.enums;

public enum PaymentMethod {

    CREDIT_CARD("Credit Card"),
    WIRE_TRANSFER("Wire Transfer"),
    CASH("Cash");
    private String title;

//    public static final Map<Integer, String> keyValues = new HashMap<>();
//
//    static {
//        for(PaymentMethod type : PaymentMethod.values()){
//            keyValues.put(type.ordinal(), type.getTitle());
//        }
//    }

    PaymentMethod(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
