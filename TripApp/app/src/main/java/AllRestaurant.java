package com.example.tripapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllRestaurant extends AppCompatActivity {


    //Recycler view for Restaurant
    List<Restaurant> data = new ArrayList<>();

    //Restaurant for Naran Side
    private String[] NaranRestaurantName = new String[]{"Moon Restaurant", "Punjab Tikka House"};
    private int[] NaranRestaurantImg = new int[]{R.drawable.not_available_image_2,R.drawable.not_available_image_2};

    //Restaurant for Hunza Side
    private String[] HunzaRestaurantName = new String[]{"Cafe de Hunza", "Glacier Breeze","Hidden Paradise Hunza"};
    private int[] HunzaRestaurantImg = new int[]{R.drawable.not_available_image_2, R.drawable.not_available_image_2,R.drawable.not_available_image_2};

    //Restaurant for Swat Side
    private String[] SwatRestaurantName = new String[]{"Swat Marina", "Hujra Restaurant","Food Bank"};
    private int[] SwatRestaurantImg = new int[]{R.drawable.not_available_image_2, R.drawable.not_available_image_2,R.drawable.not_available_image_2};

    //Restaurant for Murree Side
    private String[] MurreeRestaurantName = new String[]{"Rock Bistro","KFC", "Pizza Hut"};
    private int[] MurreeRestaurantImg = new int[]{R.drawable.not_available_image_2, R.drawable.not_available_image_2,R.drawable.not_available_image_2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_restaurant);

        //Get DestName and Img id from previous activity
        String name = getIntent().getStringExtra("Name");
        String id =getIntent().getStringExtra("ImgId");
        int Img = Integer.valueOf(id);

        //set title of Activity
        setTitle(name);

        TextView Name = (TextView) findViewById(R.id.textviewRestaurant);
        FrameLayout ImgFrame = (FrameLayout) findViewById(R.id.frameLayoutRestaurant);

        //set the name of attraction point
        Name.setText(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImgFrame.setBackground(getDrawable(Img));
        }

        if (name.equals("Naran")) {

            //if selected item is naran from main destination page then copy its relevenat Restaurant Info
            for (int i = 0; i < NaranRestaurantName.length; i++) {
                Restaurant obj = new Restaurant(NaranRestaurantName[i], NaranRestaurantImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewRestaurant);
            RecyclerViewAdapterAllRestaurants adapter = new RecyclerViewAdapterAllRestaurants(this, data);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));
        } else if (name.equals("Hunza")) {

            //if selected item is Hunza from main destination page then copy its relevenat Restaurant info
            for (int i = 0; i < HunzaRestaurantName.length; i++) {
                Restaurant obj = new Restaurant(HunzaRestaurantName[i], HunzaRestaurantImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewRestaurant);
            RecyclerViewAdapterAllRestaurants adapter = new RecyclerViewAdapterAllRestaurants(this, data);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));


        } else if (name.equals("Murree")) {

            //if selected item is Murree from main destination page then copy its relevenat Petrol Pump info
            for (int i = 0; i < MurreeRestaurantName.length; i++) {
                Restaurant obj = new Restaurant(MurreeRestaurantName[i], MurreeRestaurantImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewRestaurant);
            RecyclerViewAdapterAllRestaurants adapter = new RecyclerViewAdapterAllRestaurants(this, data);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));

        } else if (name.equals("Swat")) {

            //if selected item is Swat from main destination page then copy its relevenat Restaurant info
            for (int i = 0; i < SwatRestaurantName.length; i++) {
                Restaurant obj = new Restaurant(SwatRestaurantName[i], SwatRestaurantImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewRestaurant);
            RecyclerViewAdapterAllRestaurants adapter = new RecyclerViewAdapterAllRestaurants(this, data);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));


        }
    }
}
