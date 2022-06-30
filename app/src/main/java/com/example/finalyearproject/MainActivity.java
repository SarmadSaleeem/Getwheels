package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    RelativeLayout admin_layout;
    EditText admin_username;
    EditText admin_pass;
    Button admin_signin;

    TextView admin_switch;

    String user="admin";
    String pass="admin";

    ConstraintLayout layout;

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

        layout=findViewById(R.id.mainactivitylayout);

        admin_layout=findViewById(R.id.admin_layout);
        admin_username=findViewById(R.id.admin_username);
        admin_pass=findViewById(R.id.admin_pass);
        admin_signin=findViewById(R.id.admin_login);
        admin_switch=findViewById(R.id.admin_switch);

        admin_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setVisibility(View.GONE);
                signup.setVisibility(View.GONE);
                admin_switch.setVisibility(View.GONE);
                admin_layout.setVisibility(View.VISIBLE);

                admin_signin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username=admin_username.getText().toString();
                        String password=admin_pass.getText().toString();

                        if(username.equals(user) && password.equals(pass)){
                            Intent intent=new Intent(MainActivity.this,Admin_Activity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

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