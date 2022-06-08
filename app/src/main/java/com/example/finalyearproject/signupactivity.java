package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class signupactivity extends AppCompatActivity {

    private EditText Register_name,Register_email,Register_password,Register_confirm_password,Register_phoneno;
    private Button Register,BacktoLogin;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        Register_name=findViewById(R.id.fullname);
        Register_email=findViewById(R.id.registeremail);
        Register_password=findViewById(R.id.registerpassword);
        Register_confirm_password=findViewById(R.id.confirmpassword);
        Register_phoneno=findViewById(R.id.registerphoneno);
        Register=findViewById(R.id.register);
        BacktoLogin=findViewById(R.id.backtologin);
        progressBar=findViewById(R.id.progressBar);

        BacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent backtologin=new Intent(signupactivity.this,MainActivity.class);
               startActivity(backtologin);
            }
        });

    }

    public void onregister(View view) {

        String name=Register_name.getText().toString().trim();
        String email=Register_email.getText().toString().trim();
        String input_password=Register_password.getText().toString().trim();
        String confirm_password=Register_confirm_password.getText().toString().trim();
        String phone=Register_phoneno.getText().toString().trim();

        Validationcheck(name,email,input_password,confirm_password,phone);

    }

    private void Validationcheck(String name, String email, String input_password, String confirm_password, String phone) {

        if(name.isEmpty() || !name.matches("[a-zA-Z, ]+")) {
            Register_name.requestFocus();
            Register_name.setError("Invalid");
        }
        else if(email.isEmpty() || !email.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$")){
            Register_email.requestFocus();
            Register_email.setError("Invalid");
        }
        else if(input_password.isEmpty() || input_password.length()<6){
            Register_password.requestFocus();
            Register_password.setError("Password must be 6 character long");
        }
        else if(!input_password.equals(confirm_password)){
            Register_confirm_password.requestFocus();
            Register_confirm_password.setError("Password Not Match");
        }
        else if(phone.isEmpty()|| phone.length()<11 || !phone.matches("[+]{1}[9]{1}[2]{1}[0-9]{10}")){
            Register_phoneno.requestFocus();
            Register_phoneno.setError("+92xxxxxxxxxx Valid Format");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email, input_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        GetwheelsUsersData getwheelsUsersData = new GetwheelsUsersData(name, email, input_password, phone);

                        FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger").child("Basic Info").setValue(getwheelsUsersData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Intent intent=new Intent(signupactivity.this,MainActivity.class);
                                    startActivity(intent);
                                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Confirmation")
                                            .setValue("false");
                                  Toast.makeText(signupactivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();

                                }
                                else
                                   {
                                    Toast.makeText(signupactivity.this, ""+task.getException(), Toast.LENGTH_LONG).show();
                                }

                                progressBar.setVisibility(View.GONE);
                          }
                        });
                    } else {
                        Toast.makeText(signupactivity.this, ""+task.getException(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);

                    }

                }
            });
        }

    }
}