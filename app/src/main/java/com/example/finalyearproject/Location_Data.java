package com.example.finalyearproject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class Location_Data extends ViewModel {

    MutableLiveData<String> current_location;
    MutableLiveData<String> destination_location;

    public void initiliza(){

        current_location=new MutableLiveData<>();
        destination_location=new MutableLiveData<>();

    }

    public MutableLiveData<String> getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current) {
        this.current_location.setValue(current);
    }

    public MutableLiveData<String> getDestination_location() {
        return destination_location;
    }

    public void setDestination_location(String destination) {
        this.destination_location.setValue(destination);
    }
}