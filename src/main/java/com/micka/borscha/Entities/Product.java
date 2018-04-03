package com.micka.borscha.Entities;

import com.micka.borscha.DAO.Identified;

public class Product implements Identified<Integer> {

    private int id;
    private String product_title;
    private int product_quantity;
    private int product_minimum;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getProduct_minimum() {
        return product_minimum;
    }

    public void setProduct_minimum(int product_minimum) {
        this.product_minimum = product_minimum;
    }
}
