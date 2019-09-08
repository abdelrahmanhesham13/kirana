
package com.softclads.Gagron.Models.ItemDescribtionModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemModel {
    public ItemModel(Product product) {
        this.product = product;
    }

    @SerializedName("product")
    @Expose
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
