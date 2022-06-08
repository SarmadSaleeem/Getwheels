package com.example.finalyearproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Driver_Vehicle_info extends AppCompatActivity {

    EditText Car_NumberPlate;
    ImageView Car_Img;
    Button Submit_Vehicle_Info;
    ActivityResultLauncher<String> car_image;
    Uri Car_Img_Uri;

    public String Car_Number_Plate;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_vehicle_info);

        Car_NumberPlate=findViewById(R.id.driver_registration_Number_plate);
        Car_Img=findViewById(R.id.driver_registration_carimage);
        Submit_Vehicle_Info=findViewById(R.id.driver_registration_submit_vehicleinfo);
        progressBar=findViewById(R.id.vehicle_info_progress);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

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

        Submit_Vehicle_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car_Number_Plate=Car_NumberPlate.getText().toString();

                if(Car_Number_Plate.length()==0){
                    Car_NumberPlate.requestFocus();
                    Car_NumberPlate.setError("Required");
                }
                else if(Car_Img_Uri==null){
                    Toast.makeText(Driver_Vehicle_info.this, "Image Required", Toast.LENGTH_SHORT).show();
                }
                else {

                    progressBar.setVisibility(View.VISIBLE);

                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Driver").child("Vehicle_Info")
                            .child("Number_Plate_No").setValue(Car_Number_Plate);

                    firebaseStorage.getReference("Driver_Data").child(firebaseAuth.getCurrentUser().getUid())
                            .child("Vehicle_Img").putFile(Car_Img_Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            firebaseStorage.getReference("Driver_Data").child(firebaseAuth.getCurrentUser().getUid())
                                    .child("Vehicle_Img").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Driver")
                                            .child("Vehicle_Info").child("Vehicle_Img").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(Driver_Vehicle_info.this, "Details Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(Driver_Vehicle_info.this, "Error ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}