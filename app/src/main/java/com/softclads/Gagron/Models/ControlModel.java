package com.softclads.Gagron.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ControlModel {

    @SerializedName("control")
    @Expose
    private String control;

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

}
