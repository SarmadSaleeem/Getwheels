package com.example.finalyearproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Driver_Licence extends AppCompatActivity {

    EditText driver_licenceNo;
    ImageView driver_licenceImg;
    Button uploadlicence_info;
    Uri imguri;

    ActivityResultLauncher<String> getlicence_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_licence);

        driver_licenceNo=findViewById(R.id.driver_registration_licenceNO);
        driver_licenceImg=findViewById(R.id.driver_registration_licnencphoto);
        uploadlicence_info=findViewById(R.id.driver_registration_submit_licence);

        getlicence_img=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                driver_licenceImg.setImageURI(result);
                imguri=result;
            }
        });

        driver_licenceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlicence_img.launch("image/*");
            }
        });
    }
}