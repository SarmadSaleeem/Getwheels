package com.example.finalyearproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
        ArrayList<Integer> HondaCivic=new ArrayList<>();
        HondaCivic.add(R.drawable.hondacivic1);
        HondaCivic.add(R.drawable.hondacivic2);
        HondaCivic.add(R.drawable.hondacivic3);
        car1.setOtherimage(HondaCivic);
        holder.add(car1);

        CarData car2=new CarData();
        car2.setCarname("Honda City");
        car2.setCarprice("PKR 2800/day");
        car2.setCarimage(R.drawable.honda_city);
        ArrayList<Integer> HondaCity=new ArrayList<>();
        HondaCity.add(R.drawable.city1);
        HondaCity.add(R.drawable.city2);
        HondaCity.add(R.drawable.city3);
        car2.setOtherimage(HondaCity);
        holder.add(car2);

        CarData car3=new CarData();
        car3.setCarname("Suzuki Alto");
        car3.setCarprice("PKR 2600/day");
        car3.setCarimage(R.drawable.suzuki_alto);
        ArrayList<Integer> Alto=new ArrayList<>();
        Alto.add(R.drawable.alto1);
        Alto.add(R.drawable.alto2);
        Alto.add(R.drawable.alto3);
        car3.setOtherimage(Alto);
        holder.add(car3);

        CarData car4=new CarData();
        car4.setCarname("Suzuki Cultus");
        car4.setCarprice("PKR 2500/day");
        car4.setCarimage(R.drawable.suzuki_cultus);
        ArrayList<Integer> Cultus=new ArrayList<>();
        Cultus.add(R.drawable.cultus1);
        Cultus.add(R.drawable.cultus2);
        Cultus.add(R.drawable.cultus3);
        car4.setOtherimage(Cultus);
        holder.add(car4);

        CarData car5=new CarData();
        car5.setCarname("Suzuki WagonR");
        car5.setCarprice("PKR 3000/day");
        car5.setCarimage(R.drawable.suzuki_wagonr);
        ArrayList<Integer> WagonR=new ArrayList<>();
        WagonR.add(R.drawable.wr1);
        WagonR.add(R.drawable.wr2);
        WagonR.add(R.drawable.wr3);
        car5.setOtherimage(WagonR);
        holder.add(car5);

        CarData car6=new CarData();
        car6.setCarname("Toyota Corolla");
        car6.setCarprice("PKR 3200/day");
        car6.setCarimage(R.drawable.toyota_corolla);
        ArrayList<Integer> Corolla=new ArrayList<>();
        Corolla.add(R.drawable.corolla1);
        Corolla.add(R.drawable.corolla2);
        Corolla.add(R.drawable.corolla3);
        car6.setOtherimage(Corolla);
        holder.add(car6);

        CarData car7=new CarData();
        car7.setCarname("Toyota Yaris");
        car7.setCarprice("PKR 2900/day");
        car7.setCarimage(R.drawable.toyota_yaris);
        ArrayList<Integer> Yaris=new ArrayList<>();
        Yaris.add(R.drawable.yaris1);
        Yaris.add(R.drawable.yaris2);
        Yaris.add(R.drawable.yaris3);
        car7.setOtherimage(Yaris);
        holder.add(car7);

        CarData car8=new CarData();
        car8.setCarname("Changan Alsvin");
        car8.setCarprice("PKR 3500/day");
        car8.setCarimage(R.drawable.changan_alsvin);

        ArrayList<Integer> Alsivin=new ArrayList<>();
        Alsivin.add(R.drawable.alswin1);
        Alsivin.add(R.drawable.alswin2);
        Alsivin.add(R.drawable.alswin3);
        car8.setOtherimage(Alsivin);
        holder.add(car8);
        return holder;

    }
}