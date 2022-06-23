package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_Info extends AppCompatActivity {
    EditText change_name;
    EditText current_password;
    EditText new_password;
    EditText change_phoneNO;

    Button confirm_changes;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    String change_current_name="";
    String enter_current_password="";
    String enter_new_password="";
    String change_current_phoneno="";

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        change_name=findViewById(R.id.change_name);
        current_password=findViewById(R.id.current_password);
        new_password=findViewById(R.id.new_password);
        change_phoneNO=findViewById(R.id.change_phoneNo);

        confirm_changes=findViewById(R.id.update_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        confirm_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                change_current_name=change_name.getText().toString().trim();
                enter_current_password = current_password.getText().toString().trim();
                enter_new_password=new_password.getText().toString().trim();
                change_current_phoneno=change_phoneNO.getText().toString().trim();

                if(!change_current_name.equals("")){
                    firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger")
                            .child("Basic Info").child("username").setValue(change_current_name).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Update_Info.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else if(enter_current_password.length()>0){

                    AuthCredential authCredential=EmailAuthProvider.getCredential(firebaseUser.getEmail(),enter_current_password);
                    firebaseUser.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if(enter_new_password.length()==0 || enter_new_password.length()<6){
                                Toast.makeText(Update_Info.this, "New Password Must Be 6 Character Long", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                firebaseUser.updatePassword(enter_new_password);
                                Toast.makeText(Update_Info.this, "Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Update_Info.this, "Password Not Match", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

               else if(!change_current_phoneno.equals("")){

                    if(change_current_phoneno.length()==13 && change_current_phoneno.matches("[+]{1}[9]{1}[2]{1}[0-9]{10}")){
                        firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).child("Passenger")
                                .child("Basic Info").child("userphone").setValue(change_current_phoneno).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Update_Info.this, "Updated", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (change_current_phoneno.length()<13 || !change_current_phoneno.matches("[+]{1}[9]{1}[2]{1}[0-9]{10}")){
                        Toast.makeText(Update_Info.this, "+92******** Valid Format", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }
}