package com.example.tripapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllHotels extends AppCompatActivity implements RecyclerViewAdapterAllHotels.OnNoteListener {

    //Recycler view for Hotel
    List<Hotel> data = new ArrayList<>();
    String Refname;

    public void setName(String name) {
        this.Refname = name;
    }

    public String getName() {
        return Refname;
    }


    //Hotel for Naran Side
    private String[] NaranHotelName = new String[]{"Hotel One", "Fairy Meadows Hotel", "Millenium Inn Hotel"};
    private int[] NaranHotelImg = new int[]{R.drawable.hotel_one_murree, R.drawable.fairy_meadows_hotel, R.drawable.millenium_inn};

    //Hotel for Hunza Side
    private String[] HunzaHotelName = new String[]{"Serena Inn Hotel", "Luxus Hunza Hotel", "Hunza Darbar Hotel"};
    private int[] HunzaHotelImg = new int[]{R.drawable.serena_inn, R.drawable.luxus_hunza, R.drawable.hunza_darbar_hotel};

    //Hotel for Swat Side
    private String[] SwatHotelName = new String[]{"Swat View Hotel", "Hotel Swat Regency", "Serene Hotel", "Pearl Continental"};
    private int[] SwatHotelImg = new int[]{R.drawable.swat_view_hotel, R.drawable.hotel_swat_regency, R.drawable.swat_serene_hotel, R.drawable.peral_continental};

    //Hotel for Murree Side
    private String[] MurreeHotelName = new String[]{"Hotel Faraan", "Hotel One Murree", "Move n Pick Hotel"};
    private int[] MurreeHotelImg = new int[]{R.drawable.hotel_faraan, R.drawable.hotel_one_murree, R.drawable.mov_n_pick};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_hotels);

        //Get DestName and Img id from previous activity
        String name = getIntent().getStringExtra("Name");
        String id = getIntent().getStringExtra("ImgId");
        int Img = Integer.valueOf(id);

        //setrefName
        setName(name);

        TextView Name = (TextView) findViewById(R.id.textviewHotel);
        FrameLayout ImgFrame = (FrameLayout) findViewById(R.id.frameLayoutHotel);

        setTitle(name);

        //set the name of attraction point
        Name.setText(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImgFrame.setBackground(getDrawable(Img));
        }


        if (name.equals("Naran")) {

            //if selected item is naran from main destination page then copy its relevenat Hotel Info
            for (int i = 0; i < NaranHotelName.length; i++) {
                Hotel obj = new Hotel(NaranHotelName[i], NaranHotelImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewHotel);
            RecyclerViewAdapterAllHotels adapter = new RecyclerViewAdapterAllHotels(this, data, this);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));
        } else if (name.equals("Hunza")) {

            //if selected item is Hunza from main destination page then copy its relevenat Hotel info
            for (int i = 0; i < HunzaHotelName.length; i++) {
                Hotel obj = new Hotel(HunzaHotelName[i], HunzaHotelImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewHotel);
            RecyclerViewAdapterAllHotels adapter = new RecyclerViewAdapterAllHotels(this, data, this);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));


        } else if (name.equals("Murree")) {

            //if selected item is Murree from main destination page then copy its relevenat Hotel info
            for (int i = 0; i < MurreeHotelName.length; i++) {
                Hotel obj = new Hotel(MurreeHotelName[i], MurreeHotelImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewHotel);
            RecyclerViewAdapterAllHotels adapter = new RecyclerViewAdapterAllHotels(this, data, this);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));

        } else if (name.equals("Swat")) {

            //if selected item is Swat from main destination page then copy its relevenat Hotel info
            for (int i = 0; i < SwatHotelName.length; i++) {
                Hotel obj = new Hotel(SwatHotelName[i], SwatHotelImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewHotel);
            RecyclerViewAdapterAllHotels adapter = new RecyclerViewAdapterAllHotels(this, data, this);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));


        }


    }


    @Override
    public void onNoteClick(int position) {

        String DName = getName();
        Hotel obj;

        if (DName.equals("Naran")) {
            obj = data.get(position);
            Intent intent = new Intent(getApplicationContext(), EachHotelDetail.class);
            intent.putExtra("Name", obj.getName());
            intent.putExtra("ImgID", String.valueOf(obj.getThumbnail()));
            intent.putExtra("DestinationName", "Naran");
            startActivityForResult(intent, 1);

        } else if (DName.equals("Hunza")) {
            obj = data.get(position);
            Intent intent = new Intent(getApplicationContext(), EachHotelDetail.class);
            intent.putExtra("Name", obj.getName());
            intent.putExtra("ImgID", String.valueOf(obj.getThumbnail()));
            intent.putExtra("DestinationName", "Hunza");
            startActivityForResult(intent, 1);


        } else if (DName.equals("Swat")) {
            obj = data.get(position);
            Intent intent = new Intent(getApplicationContext(), EachHotelDetail.class);
            intent.putExtra("Name", obj.getName());
            intent.putExtra("ImgID", String.valueOf(obj.getThumbnail()));
            intent.putExtra("DestinationName", "Swat");
            startActivityForResult(intent, 1);


        } else if (DName.equals("Murree")) {
            obj = data.get(position);
            Intent intent = new Intent(getApplicationContext(), EachHotelDetail.class);
            intent.putExtra("Name", obj.getName());
            intent.putExtra("ImgID", String.valueOf(obj.getThumbnail()));
            intent.putExtra("DestinationName", "Murree");
            startActivityForResult(intent, 1);


        }


    }


}
