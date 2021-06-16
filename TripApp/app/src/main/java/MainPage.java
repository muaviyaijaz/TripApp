package com.example.tripapp;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList<String> upcoming_names = new ArrayList<>();
    ListView upcoming_list_view;
    DrawerLayout drawer;
    String Name;
    String Email;
    FirebaseAuth mAuth;
    TextView NavbarName;
    TextView NavBarEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();


        drawer =(DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        View headerView = navigationView.getHeaderView(0);

        //Setting username and email in nav_header_main
        TextView user_name = headerView.findViewById(R.id.NavBarName);
        user_name.setText(extras.getString("Name"));

        TextView user_email = headerView.findViewById(R.id.NavBarEmail);
        user_email.setText(extras.getString("Email"));


        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMainPage()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMainPage()).commit();
                break;

            case R.id.nav_liked:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentLikedVideo()).commit();
                break;

            case R.id.nav_liked_hotels:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentLikedHotel()).commit();
                break;

            case R.id.nav_Logout:
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
