package com.demo.dive.cube.model;

import com.demo.dive.cube.enums.PaymentMethod;
import com.demo.dive.cube.enums.PaymentType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supplier_payment")
public class Payment extends BaseEntity {

    private String orderId;

    private String paymentDate;
    @NotNull()
    private PaymentMethod paymentMethod;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    @Min(1)
    private Double amount;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer i) {
        this.paymentType = PaymentType.values()[i];
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer i) {
        this.paymentMethod = PaymentMethod.values()[i];
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
