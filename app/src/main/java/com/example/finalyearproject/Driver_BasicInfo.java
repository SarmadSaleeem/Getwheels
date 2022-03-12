package com.example.finalyearproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Driver_BasicInfo extends AppCompatActivity {

    EditText driver_firstname;
    EditText driver_lastname;
    EditText driver_email;
    EditText driver_password;
    Button sumbitbasic;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    public String driver_first_name;
    public String driver_last_name;
    public String email_driver;
    public String password_driver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_info_driver);

        driver_firstname=findViewById(R.id.driver_registration_fn);
        driver_lastname=findViewById(R.id.driver_registration_ln);
        driver_email=findViewById(R.id.driver_registration_email);
        sumbitbasic=findViewById(R.id.driver_registration_submit);
        driver_password=findViewById(R.id.driver_registration_password);


        sumbitbasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driver_first_name=driver_firstname.getText().toString();
                driver_last_name=driver_lastname.getText().toString();
                email_driver=driver_email.getText().toString();
                password_driver=driver_password.getText().toString();


                if(driver_first_name.length()==0){
                    driver_firstname.requestFocus();
                    driver_firstname.setError("Invalid");
                }

                else if(driver_last_name.length()==0){
                    driver_lastname.requestFocus();
                    driver_lastname.setError("Invalid");
                }

                else if(email_driver.length()==0 || !email_driver.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$")){
                    driver_email.requestFocus();
                    driver_email.setError("Invalid");
                }

                else if(password_driver.length()==0){
                    driver_password.requestFocus();
                    driver_password.setError("Invalid");
                }
            }
        });
    }
}