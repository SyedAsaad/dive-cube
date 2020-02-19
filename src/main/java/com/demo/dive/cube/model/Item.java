package com.demo.dive.cube.model;

import javax.persistence.*;

@Entity
@Table(name="item")
public class Item extends BaseEntity{

    @Column
    private String itemName;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
