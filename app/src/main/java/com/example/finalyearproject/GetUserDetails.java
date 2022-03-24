package com.example.finalyearproject;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class GetUserDetails extends AppCompatActivity {
    Button submitinfo;

    EditText date1;
    EditText date2;

    EditText rent_time;
    EditText return_time;

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

    public String Rent_Date;
    public String Return_Date;
    public String Rent_Time;
    public String Return_Time;

    DatePickerDialog.OnDateSetListener renting_date;
    DatePickerDialog.OnDateSetListener return_date;

    public int hour;
    public int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_details);

        final Calendar calendar=Calendar.getInstance();

        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        hour=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);

        submitinfo=findViewById(R.id.submitinfo);

        date1=findViewById(R.id.RentingDate);
        date2=findViewById(R.id.ReturningDate);

        rent_time=findViewById(R.id.Rent_Time);
        return_time=findViewById(R.id.Return_Time);

        idimage=findViewById(R.id.uploadid);
        licenceimage=findViewById(R.id.uploadlicence);

        firebaseStorage=firebaseStorage.getInstance();
        firebaseAuth=firebaseAuth.getInstance();
        firebaseDatabase=firebaseDatabase.getInstance();


        uploading=findViewById(R.id.imageuploadingprogress);

        StorageReference reference=firebaseStorage.getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("ID_Card_");
        StorageReference reference1=firebaseStorage.getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("licence_");

        date1.setFocusable(false);
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(GetUserDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                ,renting_date,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        date2.setFocusable(false);
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(GetUserDetails.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,return_date,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        rent_time.setFocusable(false);
        rent_time.setOnClickListener(view ->{

            TimePickerDialog dialog=new TimePickerDialog(GetUserDetails.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    rent_time.setText(hourOfDay+":"+minute);
                }
            },hour,minute,false);

            dialog.show();
        });

        return_time.setFocusable(false);
        return_time.setOnClickListener(view ->{

            TimePickerDialog dialog=new TimePickerDialog(GetUserDetails.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    return_time.setText(hourOfDay+":"+minute);
                }
            },hour,minute,false);

            dialog.show();
        });

        renting_date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;

                date1.setText(date);
            }
        };

        return_date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;

                date2.setText(date);
            }
        };

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

        idimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");

            }
        });

        licenceimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher1.launch("image/*");
            }
        });

        submitinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rent_Date=date1.getText().toString();
                Return_Date=date2.getText().toString();

                Rent_Time=rent_time.getText().toString();
                Return_Time=return_time.getText().toString();



                uploading.setVisibility(View.VISIBLE);

                if (forid != null && forlicence != null && Rent_Date.length()!=0 && Return_Date.length()!=0 && Rent_Time.length()!=0 && Return_Time.length()!=0) {

                    userdatetime dateTime=new userdatetime(Rent_Date,Return_Date,Rent_Time,Return_Time);

                    firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).child("Date_Time").setValue(dateTime);

                    reference.putFile(forid).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GetUserDetails.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Licence_Uri")
                                            .setValue(uri.toString());
                                }
                            });
                        }
                    });
                    reference1.putFile(forlicence).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Id_Card_Uri")
                                        .setValue(uri.toString());
                            }
                        });
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

        public String Rent_Date;
        public String Return_Date;
        public String Rent_Time;
        public String Return_Time;

        public userdatetime(String rentDate, String returnDate,String rent_time,String return_time) {
            this.Rent_Date = rentDate;
            this.Return_Date = returnDate;
            this.Rent_Time=rent_time;
            this.Return_Time=return_time;

        }
    }
}
