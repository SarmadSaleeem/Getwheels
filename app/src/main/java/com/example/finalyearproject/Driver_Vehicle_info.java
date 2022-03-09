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

public class Driver_Vehicle_info extends AppCompatActivity {

    EditText Car_NumberPlate;
    ImageView Car_Img;
    Button Submit_Vehicle_Info;
    ActivityResultLauncher<String> car_image;
    Uri Car_Img_Uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_vehicle_info);

        Car_NumberPlate=findViewById(R.id.driver_registration_Number_plate);
        Car_Img=findViewById(R.id.driver_registration_carimage);
        Submit_Vehicle_Info=findViewById(R.id.driver_registration_submit_vehicleinfo);

        car_image=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                Car_Img.setImageURI(result);
                Car_Img_Uri=result;

            }
        });

        Car_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car_image.launch("image/*");
            }
        });


    }
}