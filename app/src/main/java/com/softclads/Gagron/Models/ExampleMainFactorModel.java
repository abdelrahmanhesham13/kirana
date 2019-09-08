package com.softclads.Gagron.Models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleMainFactorModel {

    @SerializedName("manufacturers")
    @Expose
    private ArrayList<Manufacturer> manufacturers = null;

    public ArrayList<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(ArrayList<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }


}