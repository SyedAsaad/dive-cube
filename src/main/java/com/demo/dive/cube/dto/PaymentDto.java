package com.demo.dive.cube.dto;

import com.demo.dive.cube.config.Constants.PaymentType;
import com.demo.dive.cube.config.Constants.PaymentMethod;
import com.demo.dive.cube.model.Item;

import javax.validation.constraints.NotNull;

public class PaymentDto {

    private Long itemId;
    @NotNull
    private PaymentMethod paymentMethod;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    private Long amount;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
