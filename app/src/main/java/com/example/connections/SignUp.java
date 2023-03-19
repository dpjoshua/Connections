package com.example.connections;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    Button mRegisterBtn;


    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://connections-97d96-default-rtdb.firebaseio.com/");







    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

       // getSupportActionBar().hide(); //Hides the action bar

        setContentView(R.layout.activity_signup);

        //Obj for firebase


        EditText ed_name=findViewById(R.id.R_name);
        EditText ed_email=findViewById(R.id.R_email);
        EditText ed_phoneNo=findViewById(R.id.R_phone_number);
        EditText ed_password=findViewById(R.id.R_password);
        EditText ed_re_password=findViewById(R.id.R_Re_password);



        mRegisterBtn=findViewById(R.id.R_button);






        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Get data from Edit Text
                String name = ed_name.getText().toString();
                String email = ed_email.getText().toString();
                final String phoneNo = ed_phoneNo.getText().toString();
                String password = ed_password.getText().toString();
                String re_password=ed_re_password.getText().toString();





                // reference.setValue("Hi");

                //Check if all fields


                if(TextUtils.isEmpty(phoneNo)){
                    ed_phoneNo.setError("Number is required.");
                    return;

                }
                if (!phoneNo.matches("\\d{10}")) {
                    // The phone number does not have  10 digits and is all numbers
                    ed_phoneNo.setError("Enter the correct number.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    ed_password.setError("Password is required.");
                    return;
                }
                if(TextUtils.isEmpty(re_password)){
                    ed_re_password.setError("Password is required.");
                    return;
                }
                if(password.length() < 6) {
                    ed_password.setError("Password must be >= 6 Characters");
                    return;
                }
                if(re_password.length() < 6) {
                    ed_re_password.setError("Password must be >= 6 Characters");
                    return;
                }

                if(!(password.equals(re_password))) {
                    ed_re_password.setError("Password's should match");
                    return;
                }




                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check if phone is not registered before
                        if(snapshot.hasChild(phoneNo)){
                            Toast.makeText(SignUp.this, "Phone Number is already registered ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //sending data to firebase
                            databaseReference.child("users").child(phoneNo).child("Name").setValue(name);
                            databaseReference.child("users").child(phoneNo).child("Email").setValue(email);
                            databaseReference.child("users").child(phoneNo).child("Password").setValue(password);


                            Toast.makeText(SignUp.this, "User Registered", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

        });
    }
}
