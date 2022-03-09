package com.example.finalyearproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Basic_Info_Activity extends AppCompatActivity {

    EditText driver_firstname;
    EditText driver_lastname;
    EditText driver_email;
    EditText driver_password;
    Button sumbitbasic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_info_driver);

        driver_firstname=findViewById(R.id.driver_registration_fn);
        driver_lastname=findViewById(R.id.driver_registration_ln);
        driver_email=findViewById(R.id.driver_registration_email);
        sumbitbasic=findViewById(R.id.driver_registration_submit);
        driver_password=findViewById(R.id.driver_registration_password);
    }
}