package com.demo.dive.cube.model;

import com.demo.dive.cube.config.Constants.PaymentMethod;
import com.demo.dive.cube.config.Constants.PaymentType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "supplier_payment")
public class SupplierPayment extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Item item;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Supplier supplier;

    private String paymentDate;
    @NotNull()
    private PaymentMethod paymentMethod;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    @Min(1)
    private Long amount;

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
