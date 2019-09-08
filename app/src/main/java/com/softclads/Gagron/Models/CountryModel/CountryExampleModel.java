
package com.softclads.Gagron.Models.CountryModel;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryExampleModel {

    @SerializedName("countries")
    @Expose
    private ArrayList<CountryModel> countries = null;

    public ArrayList<CountryModel> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<CountryModel> countries) {
        this.countries = countries;
    }

}
