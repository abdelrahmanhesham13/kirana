
package com.softclads.Gagron.Models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OldOrderListModel {

    @SerializedName("orders")
    @Expose
    private ArrayList<OldOrderModel> orders = null;

    public ArrayList<OldOrderModel> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OldOrderModel> orders) {
        this.orders = orders;
    }

}
