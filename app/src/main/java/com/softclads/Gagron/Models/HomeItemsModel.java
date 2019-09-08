package com.softclads.Gagron.Models;

public class HomeItemsModel {
    String off,des,img,unit,newprice,oldprice;

    public HomeItemsModel(String off, String des, String img, String unit, String newprice, String oldprice) {
        this.off = off;
        this.des = des;
        this.img = img;
        this.unit = unit;
        this.newprice = newprice;
        this.oldprice = oldprice;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNewprice() {
        return newprice;
    }

    public void setNewprice(String newprice) {
        this.newprice = newprice;
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice;
    }
}
