package com.demo.dive.cube.config;

import java.util.HashMap;
import java.util.Map;

public class Constants {


    public static final String EMAIL_REGEX = "^(^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$)?";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


    public enum PaymentType{
        FULL_PAYMENT("Full Payment"),
        PARTIAL_PAYMENT("Partial Payment");

        private String title;
        public static final Map<Integer, String> keyValues = new HashMap<>();

        static {
            for(PaymentType type : PaymentType.values()){
                keyValues.put(type.ordinal(), type.getTitle());
            }
        }

        PaymentType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

    public enum PaymentMethod{
        CREDIT_CARD("Credit Card"),
        WIRE_TRANSFER("Wire Transfer"),
        CASH("Cash");
        private String title;
        public static final Map<Integer, String> keyValues = new HashMap<>();

        static {
            for(PaymentMethod type : PaymentMethod.values()){
                keyValues.put(type.ordinal(), type.getTitle());
            }
        }

        PaymentMethod(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
