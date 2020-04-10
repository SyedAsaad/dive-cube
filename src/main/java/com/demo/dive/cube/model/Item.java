package com.demo.dive.cube.model;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item extends BaseEntity{

    private String itemId;

    @Column
    private String itemName;

    public Item(String itemId) {
        this.itemId = itemId;
    }

    public Item() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
