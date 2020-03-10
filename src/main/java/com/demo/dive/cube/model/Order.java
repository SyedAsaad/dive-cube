package com.demo.dive.cube.model;


import com.demo.dive.cube.enums.PaymentMethod;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Table(name="supplier_order")
@Entity
public class Order extends BaseEntity {

    @NotNull
    @Column(name = "title")
    private String title;

    @OneToOne
    private Supplier supplier;

    @Column(name="payment_date")
    private String paymentDate;

    private Double amount;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @OneToMany
    @JoinColumn(name="id")
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer i) {
        this.paymentMethod = PaymentMethod.values()[i];
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addOrderDetail(){
        if(orderDetailList==null)
            orderDetailList = new ArrayList<>();
        else
            orderDetailList.add(new OrderDetail());
    }
}

