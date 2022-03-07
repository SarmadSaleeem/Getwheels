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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class GetUserDetails extends AppCompatActivity {

    Button getid;
    Button getlicence;
    Button submitinfo;

    EditText date1;
    EditText date2;

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

    public String RentingD;
    public String ReturnD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_details);

        getid=findViewById(R.id.identitycard);
        getlicence=findViewById(R.id.lisence);
        submitinfo=findViewById(R.id.submitinfo);

        date1=findViewById(R.id.RentingDate);
        date2=findViewById(R.id.ReturningDate);

        idimage=findViewById(R.id.uploadid);
        licenceimage=findViewById(R.id.uploadlicence);

        firebaseStorage=firebaseStorage.getInstance();
        firebaseAuth=firebaseAuth.getInstance();
        firebaseDatabase=firebaseDatabase.getInstance();


        uploading=findViewById(R.id.imageuploadingprogress);

        StorageReference reference=firebaseStorage.getReference().child(FirebaseAuth.getInstance().getUid()).child("ID_Card_");
        StorageReference reference1=firebaseStorage.getReference().child(FirebaseAuth.getInstance().getUid()).child("licence_");


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
                RentingD=date1.getText().toString();
                ReturnD=date2.getText().toString();

                uploading.setVisibility(View.VISIBLE);

                if (forid != null && forlicence != null && RentingD.length()!=0 && ReturnD.length()!=0) {

                    userdatetime dateTime=new userdatetime(RentingD,ReturnD);

                    firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).child("Date_Time").setValue(dateTime);

                    reference.putFile(forid).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GetUserDetails.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                    reference1.putFile(forlicence).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Files Uploaded", Toast.LENGTH_SHORT).show();
                        uploading.setVisibility(View.GONE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GetUserDetails.this, "Error Occurred", Toast.LENGTH_SHORT).show();
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

    public class userdatetime{

        public String RentDate;
        public String ReturnDate;

        public userdatetime(String rentDate, String returnDate) {
            this.RentDate = rentDate;
            this.ReturnDate = returnDate;

        }
    }
}
