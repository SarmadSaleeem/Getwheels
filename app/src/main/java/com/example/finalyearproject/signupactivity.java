package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class signupactivity extends AppCompatActivity {

    EditText fullname;
    EditText registeremail;
    EditText password;
    EditText confirmpassword;
    EditText registerphoneno;
    Button Register;
    Button BacktoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);


        fullname=findViewById(R.id.fullname);
        registeremail=findViewById(R.id.registeremail);
        password=findViewById(R.id.registerpassword);
        confirmpassword=findViewById(R.id.confirmpassword);
        registerphoneno=findViewById(R.id.registerphoneno);
        Register=findViewById(R.id.register);
        BacktoLogin=findViewById(R.id.backtologin);

        BacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent backtologin=new Intent(signupactivity.this,MainActivity.class);
               startActivity(backtologin);
            }
        });

    }

    public void onregister(View view) {

        String name=fullname.getText().toString();
        String email=registeremail.getText().toString();
        String inputpassword=password.getText().toString();
        String confirm=confirmpassword.getText().toString();
        String phone=registerphoneno.getText().toString();

        boolean check=Validationcheck(name,email,inputpassword,confirm,phone);

        if(check==true){
            Toast.makeText(getApplicationContext(), "Valid", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean Validationcheck(String name, String email, String inputpassword, String confirm, String phone) {

        if(name.isEmpty() || !name.matches("[a-zA-Z, ]+")) {
            fullname.requestFocus();
            fullname.setError("Invalid");
            return false;
        }
        else if(email.isEmpty() || !email.contains("@gmail.com")){
            registeremail.requestFocus();
            registeremail.setError("Invalid");
            return false;
        }
        else if(inputpassword.isEmpty()){
            password.requestFocus();
            password.setError("Invalid");
            return false;
        }
        else if(!inputpassword.equals(confirm)){
            confirmpassword.requestFocus();
            confirmpassword.setError("Password Not Match");
            return false;
        }
        else if(phone.isEmpty()|| phone.length()<11){
            registerphoneno.requestFocus();
            registerphoneno.setError("Invalid Number");
            return false;
        }
        else {
            return true;
        }
    }
}