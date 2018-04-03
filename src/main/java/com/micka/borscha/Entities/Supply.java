package com.micka.borscha.Entities;

import com.micka.borscha.DAO.Identified;

import java.util.Date;

public class Supply implements Identified<Integer> {

    private int id;
    private Date supply_date;
    private int vendor_id;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSupply_date() {
        return supply_date;
    }

    public void setSupply_date(Date supply_date) {
        this.supply_date = supply_date;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }
}
