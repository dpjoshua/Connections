package com.example.connections;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogIn extends AppCompatActivity {

    Button login_Button;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://connections-97d96-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText l_ed_phone = findViewById(R.id.l_phoneNo);
        final EditText l_ed_password = findViewById(R.id.l_password);
        login_Button = findViewById(R.id.l_button);


        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneNo=l_ed_phone.getText().toString();
                final String password=l_ed_password.getText().toString();

                //Check if fields are empty
                if(phoneNo.isEmpty()||password.isEmpty()){
                    Toast.makeText(LogIn.this, "Please enter your mobile number or password", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Check if mobile number exists in firebase
                            if(snapshot.hasChild(phoneNo)){
                                //mobile exists in firebase
                                //get password from user and match with firebase
                                final String getPassword=snapshot.child(phoneNo).child("Password").getValue(String.class);

                                if(getPassword.equals(password)){
                                    Toast.makeText(LogIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    openNewActivity_forHome();

                                }
                                else{
                                    Toast.makeText(LogIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(LogIn.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }



            }
        });


    }
    public void openNewActivity_forHome(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}