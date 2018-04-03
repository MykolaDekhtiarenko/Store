package com.micka.borscha.Entities;

import com.micka.borscha.DAO.Identified;

public class Vendor implements Identified<Integer> {

    private int vendorId;
    private String vendorTitle;

    @Override
    public Integer getId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorTitle() {
        return vendorTitle;
    }

    public void setVendorTitle(String vendorTitle) {
        this.vendorTitle = vendorTitle;
    }
}
