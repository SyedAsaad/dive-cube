package com.demo.dive.cube.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_detail")
    private Order order;
    @ManyToOne
    private Item item;
    @Column
    private Double amount;
    @Column
    private Integer quantity;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
