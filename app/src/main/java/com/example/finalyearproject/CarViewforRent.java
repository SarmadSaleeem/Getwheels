package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CarViewforRent extends AppCompatActivity {
    ImageView carimage;
    TextView carname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_viewfor_rent);

        carimage=findViewById(R.id.carimage);
        carname=findViewById(R.id.nameofcar);

        Intent intent=getIntent();

        carimage.setImageResource(intent.getIntExtra("carpicture",0));
        carname.setText(intent.getStringExtra("carname"));

    }
}