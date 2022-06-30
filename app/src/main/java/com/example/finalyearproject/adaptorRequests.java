package com.example.finalyearproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adaptorRequests extends RecyclerView.Adapter<adaptorRequests.MyViewholder> {



    Context context;
    ArrayList<Booking_Request_Data> list;

    public adaptorRequests(Context context, ArrayList<Booking_Request_Data> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_request_recycler,parent,false);
        MyViewholder holder=new MyViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        Booking_Request_Data data=list.get(position);

        holder.Passenger_Name.setText(data.getPassenger_Name());
        Picasso.get().load(data.getPassenger_DP()).into(holder.Profile_Picture);
        holder.Passenger_Current_Location.setText(data.getPick_Up_Location());
        holder.Passenger_Destination_Location.setText(data.getDrop_Location());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewholder extends RecyclerView.ViewHolder {

        ImageView Profile_Picture;
        TextView Passenger_Name;
        TextView Passenger_Current_Location;
        TextView Passenger_Destination_Location;

        Button Accept_Request;
        Button Cancel_Request;

        FirebaseDatabase firebaseDatabase;
        FirebaseAuth firebaseAuth;

        String Current_Location;
        String Destination_Location;
        String id;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            Profile_Picture=itemView.findViewById(R.id.Passenger_Profile);
            Passenger_Name=itemView.findViewById(R.id.Passenger_Name);
            Passenger_Current_Location=itemView.findViewById(R.id.Passenger_Current_Location);
            Passenger_Destination_Location=itemView.findViewById(R.id.Passenger_Destination_Location);

            Accept_Request=itemView.findViewById(R.id.Accept_Request);
            Cancel_Request=itemView.findViewById(R.id.Cancel_Request);

            firebaseDatabase=FirebaseDatabase.getInstance();
            firebaseAuth=FirebaseAuth.getInstance();

            Accept_Request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    NotificationCompat.Builder builder=new NotificationCompat.Builder(context.getApplicationContext(),"01");
                    builder.setSmallIcon(R.drawable.ic_baseline_android_24);
                    builder.setContentTitle("Get_Wheels");
                    builder.setContentText("Ride Accepted Wait For the Driver At Pick_Up_Location");
                    NotificationManagerCompat managerCompat=NotificationManagerCompat.from(context.getApplicationContext());
                    managerCompat.notify(1,builder.build());
                    Current_Location=Passenger_Current_Location.getText().toString();
                    Destination_Location=Passenger_Destination_Location.getText().toString();
                    DisplayTrack(Current_Location,Destination_Location);
                    firebaseDatabase.getReference("id").setValue(id);
                }
            });
        }

        private void DisplayTrack(String Current_Location, String Destination_Location) {

            try {

                Uri uri= Uri.parse("https://www.google.co.in/maps/dir/" + Current_Location + "/" + Destination_Location);

                Intent intent=new Intent(Intent.ACTION_VIEW, uri);

                intent.setPackage("com.google.android.apps.maps");

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);


            }

            catch (ActivityNotFoundException e){

                Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.maps");

                Intent intent=new Intent(Intent.ACTION_VIEW, uri);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        }
    }
}
