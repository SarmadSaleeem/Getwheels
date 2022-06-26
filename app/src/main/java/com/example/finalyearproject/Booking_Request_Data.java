package com.example.finalyearproject;

import android.net.Uri;

public class Booking_Request_Data {

    String DP_Uri;
    String Passenger_Name;
    String Passenger_Current_Location;
    String Passenger_Destination_Location;

    public Booking_Request_Data(){

    }
    public Booking_Request_Data(String DP_Uri, String passenger_Name, String passenger_Current_Location, String passenger_Destination_Location) {
        this.DP_Uri = DP_Uri;
        this.Passenger_Name = passenger_Name;
        this.Passenger_Current_Location = passenger_Current_Location;
        this.Passenger_Destination_Location = passenger_Destination_Location;
    }

    public String getDP_Uri() {
        return DP_Uri;
    }

    public void setDP_Uri(String DP_Uri) {
        this.DP_Uri = DP_Uri;
    }

    public String getPassenger_Name() {
        return Passenger_Name;
    }

    public void setPassenger_Name(String passenger_Name) {
        Passenger_Name = passenger_Name;
    }

    public String getPassenger_Current_Location() {
        return Passenger_Current_Location;
    }

    public void setPassenger_Current_Location(String passenger_Current_Location) {
        Passenger_Current_Location = passenger_Current_Location;
    }

    public String getPassenger_Destination_Location() {
        return Passenger_Destination_Location;
    }

    public void setPassenger_Destination_Location(String passenger_Destination_Location) {
        Passenger_Destination_Location = passenger_Destination_Location;
    }

}
