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

public class MainActivity extends AppCompatActivity {

    private Button login,signup;
    private EditText User_email,User_password;
    private ProgressBar progressBar;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        User_email=findViewById(R.id.useremail);
        User_password=findViewById(R.id.userpassword);
        progressBar=findViewById(R.id.progressBar2);
        mauth=FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, signupactivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptLogin();
            }
        });
    }

    private void AttemptLogin() {

        String useremail=User_email.getText().toString().trim();
        String userpassword=User_password.getText().toString().trim();

        if(useremail.isEmpty() || !useremail.contains("@gmail.com")){
            User_email.requestFocus();
            User_email.setError("Invalid");
        }
        else if(userpassword.isEmpty()){
            User_password.requestFocus();
            User_password.setError("Invalid");
        }

        else{
            progressBar.setVisibility(View.VISIBLE);
            mauth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

}