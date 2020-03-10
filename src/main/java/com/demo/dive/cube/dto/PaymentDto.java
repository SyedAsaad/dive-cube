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
    private Order orderId;
    @NotNull
    private Supplier supplierId;
    @NotNull()
    private PaymentMethod paymentMethod;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    @Min(1)
    private Double amount;
    @NotNull
    private String paymentDate;

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getPaymentType() {
        return paymentType != null ? paymentType.ordinal() : null;
    }

    public void setPaymentType(Integer i) {
        this.paymentType = PaymentType.values()[i];
    }

    public Integer getPaymentMethod() {
        return paymentMethod != null ? paymentMethod.ordinal() : null;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
