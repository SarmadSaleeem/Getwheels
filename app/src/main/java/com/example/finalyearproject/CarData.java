package com.example.finalyearproject;

import java.util.ArrayList;

public class CarData {

    private String carname;
    private String carprice;
    private int carimage;
    ArrayList<Integer> otherimage=new ArrayList<>();

    public String getCarname() {
        return carname;
    }

    public String getCarprice() {
        return carprice;
    }

    public int getCarimage() {
        return carimage;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public void setCarprice(String carprice) {
        this.carprice = carprice;
    }

    public void setCarimage(int carimage) {
        this.carimage = carimage;
    }

    public ArrayList<Integer> getOtherimage() {
        return otherimage;
    }

    public void setOtherimage(ArrayList<Integer> otherimage) {
        this.otherimage = otherimage;
    }
}
