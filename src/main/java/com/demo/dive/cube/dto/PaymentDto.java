package com.demo.dive.cube.dto;

import com.demo.dive.cube.enums.PaymentMethod;
import com.demo.dive.cube.enums.PaymentType;
import com.demo.dive.cube.model.Order;
import com.demo.dive.cube.model.Supplier;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PaymentDto {

    private Long id;
    @NotNull
    private String orderId;
    @NotNull()
    private PaymentMethod paymentMethod;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    @Min(1)
    private Double amount;
    @NotNull
    private String paymentDate;

    public PaymentDto() {
    }

    public PaymentDto(@NotNull String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
