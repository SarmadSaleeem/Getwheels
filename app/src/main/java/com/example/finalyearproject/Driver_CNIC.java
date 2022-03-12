package com.example.finalyearproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Driver_CNIC extends AppCompatActivity {

    ImageView id_front;
    ImageView id_back;
    Button sumbit_id;

    ActivityResultLauncher<String> getid_front;
    ActivityResultLauncher<String> getid_back;

    Uri front_side_id;
    Uri back_side_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_cnic);

        id_front=findViewById(R.id.driver_registration_idcardfront);
        id_back=findViewById(R.id.driver_registration_idcardback);
        sumbit_id=findViewById(R.id.driver_registration_submit_idinfo);

        getid_front=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                id_front.setImageURI(result);
                front_side_id=result;

            }
        });

        getid_back=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                id_back.setImageURI(result);
                back_side_id=result;

            }
        });

        id_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getid_front.launch("image/*");
            }
        });

        id_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getid_back.launch("image/*");
            }
        });

        sumbit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(front_side_id==null){
                    Toast.makeText(Driver_CNIC.this, "Image Required", Toast.LENGTH_SHORT).show();
                }

                else if(back_side_id==null){
                    Toast.makeText(Driver_CNIC.this, "Image Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}