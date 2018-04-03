package com.micka.borscha.Entities;

import com.micka.borscha.DAO.Identified;

import java.sql.Date;

public class Order implements Identified<Integer> {

    private int id;
    private Date order_date;
    private int fk_order_employee_id;
    private int fk_order_vendor_id;
    private int fk_order_supply_id;

    public void setId(int id) {
        this.id = id;
    }

    public Order() {
    }

    public Date getOrder_date() {
        return order_date;

    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Order(Date order_date, int fk_order_employee_id, int fk_order_vendor_id, int fk_order_supply_id) {
        this.order_date = order_date;
        this.fk_order_employee_id = fk_order_employee_id;
        this.fk_order_vendor_id = fk_order_vendor_id;
        this.fk_order_supply_id = fk_order_supply_id;
    }

    public int getFk_order_employee_id() {
        return fk_order_employee_id;
    }

    public void setFk_order_employee_id(int fk_order_employee_id) {
        this.fk_order_employee_id = fk_order_employee_id;
    }

    public int getFk_order_vendor_id() {
        return fk_order_vendor_id;
    }

    public void setFk_order_vendor_id(int fk_order_vendor_id) {
        this.fk_order_vendor_id = fk_order_vendor_id;
    }

    public int getFk_order_supply_id() {
        return fk_order_supply_id;
    }

    public void setFk_order_supply_id(int fk_order_supply_id) {
        this.fk_order_supply_id = fk_order_supply_id;
    }



    @Override
    public Integer getId() {
        return id;
    }

}
