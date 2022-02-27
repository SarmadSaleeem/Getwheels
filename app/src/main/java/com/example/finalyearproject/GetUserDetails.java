package com.example.finalyearproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class GetUserDetails extends AppCompatActivity {

    Button getid;
    Button getlicence;
    Button submitinfo;
    EditText fullname;

    ImageView idimage;
    ImageView licenceimage;
    Uri forid;
    Uri forlicence;

    ActivityResultLauncher<String> launcher;
    ActivityResultLauncher<String> launcher1;

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;

    ProgressBar uploading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_details);

        getid=findViewById(R.id.identitycard);
        getlicence=findViewById(R.id.lisence);
        submitinfo=findViewById(R.id.submitinfo);
        fullname=findViewById(R.id.uploadname);

        String name=fullname.getText().toString();

        idimage=findViewById(R.id.uploadid);
        licenceimage=findViewById(R.id.uploadlicence);

        firebaseDatabase=firebaseDatabase.getInstance();
        firebaseStorage=firebaseStorage.getInstance();
        firebaseAuth=firebaseAuth.getInstance();

        uploading=findViewById(R.id.imageuploadingprogress);

        StorageReference reference=firebaseStorage.getReference().child(FirebaseAuth.getInstance().getUid()).child("ID_Card_"+name);
        StorageReference reference1=firebaseStorage.getReference().child(FirebaseAuth.getInstance().getUid()).child("licence_"+name);


        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                idimage.setImageURI(result);
                forid=result;

            }
        });

        launcher1=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                licenceimage.setImageURI(result);
                forlicence=result;

            }
        });

        getid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");

            }
        });

        getlicence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher1.launch("image/*");
            }
        });

        submitinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploading.setVisibility(View.VISIBLE);

                if (forid != null && forlicence != null) {

                    reference.putFile(forid);
                    reference1.putFile(forlicence).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Files Uploaded", Toast.LENGTH_SHORT).show();
                        uploading.setVisibility(View.GONE);
                    }
                });
            }
                else {
                    Toast.makeText(getApplicationContext(), "Fields Required", Toast.LENGTH_SHORT).show();
                    uploading.setVisibility(View.GONE);
                }
            }
        });
    }
}