package com.example.connections;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://connections-97d96-default-rtdb.firebaseio.com/");
    String getName;
    String getEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // Retrieve the phone number from the intent
        String phoneNo = getIntent().getStringExtra("phoneNo");

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();








        //get from Database
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Check if mobile number exists in firebase
                if(snapshot.hasChild(phoneNo)){
                    //mobile exists in firebase
                    //get password from user and match with firebase
                    getName=snapshot.child(phoneNo).child("Name").getValue(String.class);
                    getEmail=snapshot.child(phoneNo).child("Email").getValue(String.class);


                    if (savedInstanceState == null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("phoneNo", phoneNo);
                        bundle.putString("getEmail", getEmail);
                        bundle.putString("getName", getName);


                        HomeFragment homeFragment = new HomeFragment();
                        homeFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                homeFragment).commit();
                        navigationView.setCheckedItem(R.id.nav_home);

                        // Update the email in nav_header
                        View headerView = navigationView.getHeaderView(0);
                        TextView emailTextView = headerView.findViewById(R.id.emailTextView);
                        emailTextView.setText(getEmail);
                        TextView nameTextView = headerView.findViewById(R.id.nameTextView);
                        nameTextView.setText(getName);

                    }

                }
                else {
                    Toast.makeText(MainActivity2.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("phoneNo", getIntent().getStringExtra("phoneNo"));
        bundle.putString("getEmail", getEmail);
        bundle.putString("getName", getName);
        switch (item.getItemId()) {
            case R.id.nav_home:
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        homeFragment).commit();
                break;
            case R.id.nav_data_plans:
                DataPlansFragment dataPlansFragment = new DataPlansFragment();
                dataPlansFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        dataPlansFragment).commit();
                break;
            case R.id.nav_faq:
                FaqFragment faqFragment = new FaqFragment();
                faqFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        faqFragment).commit();
                break;
            case R.id.nav_inter_roaming:
                InternationalPlansFragment internationalPlansFragment = new InternationalPlansFragment();
                internationalPlansFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        internationalPlansFragment).commit();
                break;
            case R.id.nav_my_plans:
                MyPlansFragment myPlansFragment = new MyPlansFragment();
                myPlansFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        myPlansFragment).commit();
                break;
            case R.id.nav_offers:
                OffersFragment offersFragment = new OffersFragment();
                offersFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        offersFragment).commit();
                break;
            case R.id.nav_profile:
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        profileFragment).commit();
                break;
            case R.id.nav_raise_ticket:
                RaiseTicketFragment raiseTicketFragment = new RaiseTicketFragment();
                raiseTicketFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        raiseTicketFragment).commit();
                break;
            case R.id.nav_regular_plans:
                RegularPlansFragment regularPlansFragment = new RegularPlansFragment();
                regularPlansFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        regularPlansFragment).commit();
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;



        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


