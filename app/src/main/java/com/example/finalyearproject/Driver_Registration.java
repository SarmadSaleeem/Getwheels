package com.example.finalyearproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Driver_Registration extends AppCompatActivity {

    RelativeLayout basicinfo;
    RelativeLayout licence;
    RelativeLayout cnic;
    RelativeLayout vehicle_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_registration);

        basicinfo=findViewById(R.id.driver_basicinfo);
        licence=findViewById(R.id.driver_driverlicence);
        cnic=findViewById(R.id.driver_cnic);
        vehicle_info=findViewById(R.id.driver_vehicleinfo);

        basicinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent basicinfo=new Intent(Driver_Registration.this, Basic_Info_Activity.class);
                startActivity(basicinfo);
            }
        });

        licence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent licnece=new Intent(Driver_Registration.this,Driver_Licence.class);
                startActivity(licnece);
            }
        });

        cnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cnic=new Intent(Driver_Registration.this,Driver_CNIC.class);
                startActivity(cnic);
            }
        });

        vehicle_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vehicle_info=new Intent(Driver_Registration.this,Driver_Vehicle_info.class);
                startActivity(vehicle_info);
            }
        });
    }
}
