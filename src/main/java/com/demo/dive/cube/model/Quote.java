package com.demo.dive.cube.model;

import javax.persistence.*;

@Entity
@Table(name="quote")
public class Quote extends  BaseEntity{

    @ManyToOne
    private Item item;

    @Column
    private String description;

    @Column
    private Double pricePerItem;

    @Column
    private Integer quantity;

    @Column
    private Double total;


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(Double pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
