
package com.softclads.Gagron.Models.AdresssModel;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdressExampleModel {

    @SerializedName("addresses")
    @Expose
    private ArrayList<AddressModell> addresses = null;

    public ArrayList<AddressModell> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<AddressModell> addresses) {
        this.addresses = addresses;
    }

}
