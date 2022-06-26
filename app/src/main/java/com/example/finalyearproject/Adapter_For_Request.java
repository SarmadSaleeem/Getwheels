package com.example.finalyearproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Adapter_For_Request extends FirebaseRecyclerAdapter<Booking_Request_Data,Adapter_For_Request.myviewholder>{


    public Adapter_For_Request(@NonNull FirebaseRecyclerOptions<Booking_Request_Data> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Booking_Request_Data model) {

        holder.Passenger_Name.setText(model.getPassenger_Name());
        Picasso.get().load(model.getDP_Uri()).into(holder.Profile_Picture);
        holder.Passenger_Current_Location.setText(model.getPassenger_Current_Location());
        holder.Passenger_Destination_Location.setText(model.getPassenger_Destination_Location());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_request_recycler,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView Profile_Picture;
        TextView Passenger_Name;
        TextView Passenger_Current_Location;
        TextView Passenger_Destination_Location;

        Button Accept_Request;
        Button Cancel_Request;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            Profile_Picture=itemView.findViewById(R.id.Passenger_Profile);
            Passenger_Name=itemView.findViewById(R.id.Passenger_Name);
            Passenger_Current_Location=itemView.findViewById(R.id.Passenger_Current_Location);
            Passenger_Destination_Location=itemView.findViewById(R.id.Passenger_Destination_Location);

            Accept_Request=itemView.findViewById(R.id.Accept_Request);
            Cancel_Request=itemView.findViewById(R.id.Cancel_Request);
        }
    }
}
