package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class CarViewforRent extends AppCompatActivity {
    //ImageView carimage;
    TextView carname;
    ArrayList<Integer> otherimage;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_viewfor_rent);

        viewPager=findViewById(R.id.carimage);
        otherimage=new ArrayList<>();

       // carimage=findViewById(R.id.carimage);
        carname=findViewById(R.id.nameofcar);

        Intent intent=getIntent();

        //carimage.setImageResource(intent.getIntExtra("carpicture",0));
        carname.setText(intent.getStringExtra("carname"));
        otherimage=intent.getIntegerArrayListExtra("otherimages");

        Slider slider=new Slider(otherimage);

        viewPager.setAdapter(slider);

    }
}