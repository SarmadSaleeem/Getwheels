package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;

public class Renting extends AppCompatActivity {

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting);

        searchView=findViewById(R.id.search);


        String []carname_array = getResources().getStringArray(R.array.carname);
        String []carprice_array=getResources().getStringArray(R.array.priceperday);
        int []cars= {R.drawable.honda_civic,R.drawable.honda_city,R.drawable.suzuki_alto,R.drawable.suzuki_cultus,
                R.drawable.suzuki_wagonr,R.drawable.toyota_corolla,R.drawable.toyota_yaris,R.drawable.changan_alsvin};

        RecyclerView recyclerView=findViewById(R.id.recycerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(carname_array,carprice_array,cars,this));


    }
}