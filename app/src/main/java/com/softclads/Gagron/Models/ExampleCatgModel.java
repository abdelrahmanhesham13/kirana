
package com.softclads.Gagron.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleCatgModel {

    @SerializedName("categories")
    @Expose
    private List<CategoryModel> categories = null;

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }

}
