package com.demo.dive.cube.config;

public class Constants {


    public static final String EMAIL_REGEX = "^(^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$)?";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


    public enum PaymentType{
        FULL_PAYMENT("Full Payment", "FULL_PAYMENT"),
        PARTIAL_PAYMENT("Partial Payment","PARTIAL_PAYMENT");

        private String code;
        private String title;

        PaymentType(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public String getCode() {
            return code;
        }
    }

    public enum PaymentMethod{
        CREDIT_CARD("Credit Card", "CREDIT_CARD"),
        WIRE_TRANSFER("Wire Transfer", "WIRE_TRANSFER"),
        CASH("Cash","CASH");
        private String code;
        private String title;

        PaymentMethod(String title,String code ) {
            this.code = code;
            this.title = title;
        }

        public String getCode() {
            return code;
        }
    }
}
