package com.softclads.Gagron.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleSubCatgModel {

    @SerializedName("subcategories")
    @Expose
    private List<CategoryModel> subcategories = null;

    public List<CategoryModel> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoryModel> subcategories) {
        this.subcategories = subcategories;
    }

}
