
package com.softclads.Gagron.Models.ItemDescribtionModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    public Review(String id, String customerId, String customerName, String title, String reviewText, String rating) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.title = title;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("review_text")
    @Expose
    private String reviewText;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
