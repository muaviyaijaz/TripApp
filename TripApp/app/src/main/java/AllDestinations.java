package com.example.tripapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllDestinations extends AppCompatActivity implements RecyclerViewAdapterAllDestination.OnNoteListener {

    List<Destination> mdata = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_destination);

        mdata.add(new Destination("Naran",R.drawable.naran));
        mdata.add(new Destination("Hunza",R.drawable.hunza));
        mdata.add(new Destination("Murree",R.drawable.murree));
        mdata.add(new Destination("Swat",R.drawable.swat));

        RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewAllDestination);

        RecyclerViewAdapterAllDestination adapter = new RecyclerViewAdapterAllDestination(this,mdata,this);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onNoteClick(int position) {
        Destination name = mdata.get(position);

        String DestinationName=name.getName();
        int DestinationImg=name.getThumbnail();
        String DestinationImg1 = String.valueOf(DestinationImg);


        Intent intent = new Intent(getApplicationContext(), DestinationPage.class);
        intent.putExtra("Name",DestinationName);
        intent.putExtra("ImgId",DestinationImg1);
        startActivityForResult(intent,1);
    }
}
