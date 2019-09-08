package com.softclads.Gagron.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ORDER_TO_SEND_MODEL {
    @SerializedName("product_id")
    @Expose
    ArrayList<String> product_id;
    @SerializedName("quantity")
    @Expose
    ArrayList<String> quantity;
    @SerializedName("price")
    @Expose
    ArrayList<String> price;
    @SerializedName("customer_id")
    @Expose
    String customer_id;
    @SerializedName("total_price")
    @Expose
    String total_price;
    @SerializedName("address_id")
    @Expose
    String address_id;
    @SerializedName("subtotal")
    @Expose
    String subtotal;

    public ORDER_TO_SEND_MODEL(ArrayList<String> product_id, ArrayList<String> quantity
            , ArrayList<String> price, String customer_id, String total_price, String address_id, String subtotal) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.customer_id = customer_id;
        this.total_price = total_price;
        this.address_id = address_id;
        this.subtotal = subtotal;
    }

    public ArrayList<String> getProduct_id() {
        return product_id;
    }

    public void setProduct_id(ArrayList<String> product_id) {
        this.product_id = product_id;
    }

    public ArrayList<String> getQuantity() {
        return quantity;
    }

    public void setQuantity(ArrayList<String> quantity) {
        this.quantity = quantity;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<String> price) {
        this.price = price;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
