
package com.softclads.Gagron.Models.SpecificItemModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecificItemExampleModel {

    @SerializedName("product")
    @Expose
    private ItemProductModel product;

    public ItemProductModel getProduct() {
        return product;
    }

    public void setProduct(ItemProductModel product) {
        this.product = product;
    }

}
