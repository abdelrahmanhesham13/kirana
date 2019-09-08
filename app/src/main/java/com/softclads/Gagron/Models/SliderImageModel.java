
package com.softclads.Gagron.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderImageModel {

    @SerializedName("slider_images")
    @Expose
    private ArrayList<String> sliderImages = null;

    public ArrayList<String> getSliderImages() {
        return sliderImages;
    }

    public void setSliderImages(ArrayList<String> sliderImages) {
        this.sliderImages = sliderImages;
    }

}
