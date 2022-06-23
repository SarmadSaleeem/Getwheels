package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Driver_Mode_ON extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mode_on);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        toolbar= (Toolbar) findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);

        navigationView=(NavigationView)findViewById(R.id.drawer_driver);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout_driver);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View header_view=navigationView.getHeaderView(0);
        TextView set_name=header_view.findViewById(R.id.Assign_Name);
        ImageView set_dp=header_view.findViewById(R.id.Assign_Profile);

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger").child("Basic Info")
                .child("DP Uri").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image=snapshot.getValue(String.class);
                Picasso.get().load(image).into(set_dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger").child("Basic Info")
                .child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.getValue(String.class);
                set_name.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.Switch_Passenger){
                    Intent intent=new Intent(Driver_Mode_ON.this,HomeActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });







    }
}