package com.softclads.Gagron.Models;

public class ORDERMODEL {
    String ProductId,Quantity,totalPrice;
    String Name,Image,price;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ORDERMODEL(String productId, String quantity, String totalPrice, String name, String image, String price) {
        ProductId = productId;
        Quantity = quantity;
        this.totalPrice = totalPrice;
        Name = name;

        Image = image;
        this.price = price;
    }

    public ORDERMODEL(String productId, String quantity, String totalPrice) {
        ProductId = productId;
        Quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getProductId() {

        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
