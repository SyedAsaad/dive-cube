package com.demo.dive.cube.dto;

import com.demo.dive.cube.config.Constants.PaymentMethod;
import com.demo.dive.cube.config.Constants.PaymentType;
import com.demo.dive.cube.model.Item;
import com.demo.dive.cube.model.Supplier;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PaymentDto {

    private Item itemId;

    private Supplier supplierId;
    @NotNull()
    private PaymentMethod paymentMethod;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    @Pattern(regexp = "[0-9]+", message = "Digits only allowed")
    @Min(1)
    private Long amount;
    private String paymentDate;

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }


    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
}
