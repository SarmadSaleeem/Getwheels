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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class Driver_Licence extends AppCompatActivity {

    EditText driver_licenceNo;
    ImageView driver_licenceImg;
    Button uploadlicence_info;
    Uri imguri;

    public String Driver_licence_No;

    ActivityResultLauncher<String> getlicence_img;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_licence);

        driver_licenceNo=findViewById(R.id.driver_registration_licenceNO);
        driver_licenceImg=findViewById(R.id.driver_registration_licnencphoto);
        uploadlicence_info=findViewById(R.id.driver_registration_submit_licence);
        progressBar=findViewById(R.id.driver_licence_progress);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

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

        uploadlicence_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Driver_licence_No=driver_licenceNo.getText().toString();

                if(Driver_licence_No.length()==0){
                    driver_licenceNo.requestFocus();
                    driver_licenceNo.setError("Required");
                }

                else if(imguri==null){
                    Toast.makeText(Driver_Licence.this, "Image Required", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);

                    firebaseDatabase.getReference("Driver").child(firebaseAuth.getCurrentUser().getUid()).child("Licence")
                            .child("Licence_No").setValue(Driver_licence_No);

                    firebaseStorage.getReference("Driver_Data").child(firebaseAuth.getCurrentUser().getUid())
                            .child("Licence_Img").putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            firebaseStorage.getReference("Driver_Data").child(firebaseAuth.getCurrentUser().getUid())
                                    .child("Licence_Img").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    firebaseDatabase.getReference("Driver").child(firebaseAuth.getCurrentUser().getUid()).child("Licence")
                                            .child("Licence_Img").setValue(imguri.toString());

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Driver_Licence.this, "Details Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
            }
        });
    }
}