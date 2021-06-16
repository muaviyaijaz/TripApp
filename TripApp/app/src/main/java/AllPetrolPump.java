package com.example.tripapp;

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

public class AllPetrolPump extends AppCompatActivity {

    //Recycler view for Petrol Pump
    List<PetrolPump> data = new ArrayList<>();

    //Petrol Pump for Naran Side
    private String[] NaranPetrolPumpName = new String[]{"PSO", "Shell", "Attock"};
    private int[] NaranPetrolPumpImg = new int[]{R.drawable.not_available_image_2, R.drawable.not_available_image_2, R.drawable.not_available_image_2};

    //Petrol Pump for Hunza Side
    private String[] HunzaPetrolPumpName = new String[]{"Caltex", "Eco", "Total", "Eco"};
    private int[] HunzaPetrolPumpImg = new int[]{R.drawable.not_available_image_2, R.drawable.not_available_image_2, R.drawable.not_available_image_2, R.drawable.not_available_image_2};

    //Petrol Pump for Swat Side
    private String[] SwatPetrolPumpName = new String[]{"PSO", "Shell"};
    private int[] SwatPetrolPumpImg = new int[]{R.drawable.not_available_image_2, R.drawable.not_available_image_2};

    //Petrol Pump for Murree Side
    private String[] MurreePetrolPumpName = new String[]{"PSO", "Attock"};
    private int[] MurreePetrolPumpImg = new int[]{R.drawable.not_available_image_2, R.drawable.not_available_image_2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_petrolpump);

        //Get DestName and Img id from previous activity
        String name = getIntent().getStringExtra("Name");
        String id = getIntent().getStringExtra("ImgId");
        int Img = Integer.valueOf(id);

        TextView Name = (TextView) findViewById(R.id.textviewpetrolpump);
        FrameLayout ImgFrame = (FrameLayout) findViewById(R.id.frameLayoutpetrolpump);

        //set the name of attraction point
        Name.setText(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImgFrame.setBackground(getDrawable(Img));
        }

        if (name.equals("Naran")) {

            //if selected item is naran from main destination page then copy its relevenat Petrol Pump Info
            for (int i = 0; i < NaranPetrolPumpName.length; i++) {
                PetrolPump obj = new PetrolPump(NaranPetrolPumpName[i], NaranPetrolPumpImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewPetrolPump);
            RecyclerViewAdapterAllPetrolPump adapter = new RecyclerViewAdapterAllPetrolPump(this, data);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));
        } else if (name.equals("Hunza")) {

            //if selected item is Hunza from main destination page then copy its relevenat Petrol Pump info
            for (int i = 0; i < HunzaPetrolPumpName.length; i++) {
                PetrolPump obj = new PetrolPump(HunzaPetrolPumpName[i], HunzaPetrolPumpImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewPetrolPump);
            RecyclerViewAdapterAllPetrolPump adapter = new RecyclerViewAdapterAllPetrolPump(this, data);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));


        } else if (name.equals("Murree")) {

            //if selected item is Murree from main destination page then copy its relevenat Petrol Pump info
            for (int i = 0; i < MurreePetrolPumpName.length; i++) {
                PetrolPump obj = new PetrolPump(MurreePetrolPumpName[i], MurreePetrolPumpImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewPetrolPump);
            RecyclerViewAdapterAllPetrolPump adapter = new RecyclerViewAdapterAllPetrolPump(this, data);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));

        } else if (name.equals("Swat")) {

            //if selected item is Swat from main destination page then copy its relevenat Petrol Pump info
            for (int i = 0; i < SwatPetrolPumpName.length; i++) {
                PetrolPump obj = new PetrolPump(SwatPetrolPumpName[i], SwatPetrolPumpImg[i]);
                data.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewPetrolPump);
            RecyclerViewAdapterAllPetrolPump adapter = new RecyclerViewAdapterAllPetrolPump(this, data);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));


        }


    }
}
