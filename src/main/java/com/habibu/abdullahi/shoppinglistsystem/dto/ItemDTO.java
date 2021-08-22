package com.habibu.abdullahi.shoppinglistsystem.dto;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@Entity
public class ItemDTO {
    
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @NotEmpty
    @Length(max = 180)
    @Column(name = "PRODUCTNAME")
    private String productname;

    @NotNull
    @Column(name = "PRICE")
    private Long price;

    @NotNull
    @Column(name = "QUANTITY")
    private Long quantity;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
