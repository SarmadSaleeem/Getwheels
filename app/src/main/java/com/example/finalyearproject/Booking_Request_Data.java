package com.example.finalyearproject;

import android.net.Uri;

public class Booking_Request_Data {


    String Drop_Location;
    String Passenger_DP;
    String Passenger_Name;
    String Pick_Up_Location;

    /*
    Booking_Request_Data(){

    }

    public Booking_Request_Data(String Drop_Location, String Passenger_DP, String Passenger_Name, String Pick_Up_Location) {
        this.Drop_Location=Drop_Location;
        this.Passenger_DP=Passenger_DP;
        this.Passenger_Name=Passenger_Name;
        this.Pick_Up_Location=Pick_Up_Location;
    }

     */

    public String getPassenger_DP() {
        return Passenger_DP;
    }

    public String getPassenger_Name() {
        return Passenger_Name;
    }

    public String getPick_Up_Location() {
        return Pick_Up_Location;
    }

    public String getDrop_Location() {
        return Drop_Location;
    }


}
