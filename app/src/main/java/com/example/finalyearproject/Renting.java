package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;

public class Renting extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting);

        SearchView searchView= (SearchView) findViewById(R.id.search);
        int id = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.BLACK);
    }
}