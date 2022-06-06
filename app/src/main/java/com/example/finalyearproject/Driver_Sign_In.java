package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Driver_Sign_In extends AppCompatActivity {

    EditText driver_email,driver_password;
    Button signin_driver,signupdriver;
    TextView passenger_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_in);

        driver_email=findViewById(R.id.useremail_driver);
        driver_password=findViewById(R.id.userpassword_driver);

        signin_driver=findViewById(R.id.login_driver);
        signupdriver=findViewById(R.id.signup_driver);

        passenger_login=findViewById(R.id.passenger_login);

        passenger_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Driver_Sign_In.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}