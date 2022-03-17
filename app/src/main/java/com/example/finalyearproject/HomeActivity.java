package com.example.finalyearproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity{
    Button carrenting;
    Button carbooking;

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    ActivityResultLauncher<String> dp;

    public Uri mydp;

    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        carrenting=findViewById(R.id.carrenting);
        carbooking=findViewById(R.id.carbooking);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView=(NavigationView)findViewById(R.id.drawer);
        drawerLayout=(DrawerLayout)findViewById(R.id.mydrawerlayout);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        View header_view=navigationView.getHeaderView(0);
        TextView set_name=header_view.findViewById(R.id.Assign_Name);
        ImageView set_dp=header_view.findViewById(R.id.Assign_Profile);

        dp=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                set_dp.setImageURI(result);
                mydp=result;

                firebaseStorage.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("DP").putFile(mydp).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        firebaseStorage.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("DP")
                                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid())
                                        .child("DP Uri").setValue(uri.toString());
                            }
                        });
                    }
                });
            }
        });

        set_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dp.launch("image/*");
            }
        });

        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid())
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.register_icon){
                    Intent driverregistration=new Intent(HomeActivity.this,Driver_Registration.class);
                    startActivity(driverregistration);
                }
                return true;
            }
        });

        carrenting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,Renting.class);
                startActivity(intent);
            }
        });
        
    }
}