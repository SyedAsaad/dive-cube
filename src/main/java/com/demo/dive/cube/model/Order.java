package com.demo.dive.cube.model;


import com.demo.dive.cube.enums.PaymentMethod;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="supplier_order")
public class Order extends BaseEntity {

    @Column
    private String title;

    @Column
    private String orderId;

    @ManyToOne
    private Supplier supplier;

    @Column(name="order_date")
    private String orderDate;

    @Column
    private Double amount = 0.0;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "tracking_num")
    private String trackingNum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true,mappedBy = "order")
    @Column(name="order_detail")
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public void addOrderDetail(){
        if(orderDetailList==null)
            orderDetailList = new ArrayList<>();
        else
            orderDetailList.add(new OrderDetail());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }
}

