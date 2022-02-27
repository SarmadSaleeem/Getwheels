package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class CarViewforRent extends AppCompatActivity {
    //ImageView carimage;
    TextView carname;
    ArrayList<Integer> otherimage;
    ViewPager viewPager;

    Button booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_viewfor_rent);

        viewPager=findViewById(R.id.carimage);
        otherimage=new ArrayList<>();

       // carimage=findViewById(R.id.carimage);
        carname=findViewById(R.id.nameofcar);

        booking=findViewById(R.id.clickbook);

        Intent intent=getIntent();

        //carimage.setImageResource(intent.getIntExtra("carpicture",0));
        carname.setText(intent.getStringExtra("carname"));
        otherimage=intent.getIntegerArrayListExtra("otherimages");
        Slider slider=new Slider(otherimage);
        viewPager.setAdapter(slider);


        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myintent = new Intent(CarViewforRent.this,GetUserDetails.class);
                startActivity(myintent);
            }
        });

    }
}