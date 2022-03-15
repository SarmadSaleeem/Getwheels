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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Driver_CNIC extends AppCompatActivity {

    ImageView id_front;
    ImageView id_back;
    Button sumbit_id;

    ActivityResultLauncher<String> getid_front;
    ActivityResultLauncher<String> getid_back;

    Uri front_side_id;
    Uri back_side_id;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;

    StorageReference reference_front_id;
    StorageReference reference_back_id;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_cnic);

        id_front=findViewById(R.id.driver_registration_idcardfront);
        id_back=findViewById(R.id.driver_registration_idcardback);
        sumbit_id=findViewById(R.id.driver_registration_submit_idinfo);
        progressBar=findViewById(R.id.upload_id_progress);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        reference_front_id=firebaseStorage.getReference("Driver_Data").child(firebaseAuth.getCurrentUser().getUid()).child("CNIC").
                child("CNIC_FrontSide");

        reference_back_id=firebaseStorage.getReference("Driver_Data").child(firebaseAuth.getCurrentUser().getUid()).child("CNIC").
                child("CNIC_BackSide");

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
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    reference_front_id.putFile(front_side_id).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            reference_front_id.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    firebaseDatabase.getReference("Driver").child(firebaseAuth.getCurrentUser().getUid()).child("CNIC").child("Front_Side")
                                            .setValue(uri.toString());
                                }
                            });

                        }
                    });

                    reference_back_id.putFile(back_side_id).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference_back_id.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    firebaseDatabase.getReference("Driver").child(firebaseAuth.getCurrentUser().getUid()).child("CNIC").child("Back_Side")
                                            .setValue(uri.toString());

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Driver_CNIC.this, "Files Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}