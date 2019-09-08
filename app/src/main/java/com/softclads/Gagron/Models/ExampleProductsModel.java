
package com.softclads.Gagron.Models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleProductsModel {

    @SerializedName("products")
    @Expose
    private ArrayList<ProductsModel> products = null;

    public ArrayList<ProductsModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductsModel> products) {
        this.products = products;
    }

}
