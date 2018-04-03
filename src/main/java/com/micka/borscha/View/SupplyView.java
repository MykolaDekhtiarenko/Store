package com.micka.borscha.View;

import java.sql.Date;

public class SupplyView {

    private int supply_id;
    private Date supply_date;
    private int fk_vendor_id;
    private String vendor_title;

    public int getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(int supply_id) {
        this.supply_id = supply_id;
    }

    public Date getSupply_date() {
        return supply_date;
    }

    public void setSupply_date(Date supply_date) {
        this.supply_date = supply_date;
    }

    public int getFk_vendor_id() {
        return fk_vendor_id;
    }

    public void setFk_vendor_id(int fk_vendor_id) {
        this.fk_vendor_id = fk_vendor_id;
    }

    public String getVendor_title() {
        return vendor_title;
    }

    public void setVendor_title(String vendor_title) {
        this.vendor_title = vendor_title;
    }
}
