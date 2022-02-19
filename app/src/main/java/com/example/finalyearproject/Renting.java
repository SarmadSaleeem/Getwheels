package com.example.finalyearproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class Renting extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_renting);

        searchView=findViewById(R.id.searchview);

        myAdapter=new MyAdapter(this,SettingCarData());

        recyclerView=findViewById(R.id.recycerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    public ArrayList<CarData> SettingCarData(){

        ArrayList<CarData> holder=new ArrayList<>();

        CarData car1=new CarData();
        car1.setCarname("Honda Civic");
        car1.setCarprice("PKR 3000/day");
        car1.setCarimage(R.drawable.honda_civic);
        holder.add(car1);

        CarData car2=new CarData();
        car2.setCarname("Honda City");
        car2.setCarprice("PKR 2800/day");
        car2.setCarimage(R.drawable.honda_city);
        holder.add(car2);

        CarData car3=new CarData();
        car3.setCarname("Suzuki Alto");
        car3.setCarprice("PKR 2600/day");
        car3.setCarimage(R.drawable.suzuki_alto);
        holder.add(car3);

        CarData car4=new CarData();
        car4.setCarname("Suzuki Cultus");
        car4.setCarprice("PKR 2500/day");
        car4.setCarimage(R.drawable.suzuki_cultus);
        holder.add(car4);

        CarData car5=new CarData();
        car5.setCarname("Suzuki WagonR");
        car5.setCarprice("PKR 3000/day");
        car5.setCarimage(R.drawable.suzuki_wagonr);
        holder.add(car5);

        CarData car6=new CarData();
        car6.setCarname("Toyota Corolla");
        car6.setCarprice("PKR 3200/day");
        car6.setCarimage(R.drawable.toyota_corolla);
        holder.add(car6);

        CarData car7=new CarData();
        car7.setCarname("Toyota Yaris");
        car7.setCarprice("PKR 2900/day");
        car7.setCarimage(R.drawable.toyota_yaris);
        holder.add(car7);

        CarData car8=new CarData();
        car8.setCarname("Changan Alsvin");
        car8.setCarprice("PKR 3500/day");
        car8.setCarimage(R.drawable.changan_alsvin);
        holder.add(car8);
        return holder;

    }
}