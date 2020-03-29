package com.demo.dive.cube.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {

    private Item item;
    private Double amount;
    private Integer quantity;


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
