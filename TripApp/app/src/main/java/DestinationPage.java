package com.example.tripapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DestinationPage extends AppCompatActivity implements RecyclerViewAdapter.OnNoteListener {

    List<DestinationM> mdata = new ArrayList<>();

    List<LikedAreas> like = new ArrayList<>();

    int ImgId;
    String DestName;
    String Img;

    public void setDestName(String destName) {
        DestName = destName;
    }

    public String getDestName() {
        return DestName;
    }


    void setImgId(int ImgID) {
        this.ImgId = ImgID;
    }

    int getImgId() {
        return ImgId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination_main_page);

        Intent intent = this.getIntent();

        if (intent.getStringExtra("Name") == null || intent.getStringExtra("ImgId") == null) {
            DestName = getIntent().getStringExtra("SearchName");
            Img = getIntent().getStringExtra("SearchImage");
            ImgId = Integer.valueOf(Img);
        }
        if (intent.getStringExtra("SearchName") == null || intent.getStringExtra("SearchImage") == null) {

            //Receive the name of destination and figure details
            DestName = getIntent().getStringExtra("Name");
            Img = getIntent().getStringExtra("ImgId");
            ImgId = Integer.valueOf(Img);
        }

        setImgId(ImgId);

        setDestName(DestName);

//        //Receive the name of destination and figure details
//        DestName = getIntent().getStringExtra("Name");
//         Img = getIntent().getStringExtra("ImgId");
//             ImgId = Integer.valueOf(Img);
//
//        if(DestName.isEmpty() || Img.isEmpty() ){
//            DestName = getIntent().getStringExtra("SearchName");
//            Img = getIntent().getStringExtra("SearchImage");
//            ImgId=Integer.valueOf(Img);
//        }


        TextView DestinationName = (TextView) findViewById(R.id.DestinationName);
        FrameLayout DestinationImage = (FrameLayout) findViewById(R.id.frameLayoutDest);

        DestinationName.setText(DestName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DestinationImage.setBackground(getDrawable(ImgId));
        }

        //final ImageView Img1 = (ImageView) findViewById(R.id.LikeDestMainPage);
       // Img1.setOnClickListener(new View.OnClickListener() {
            //@Override
           // public void onClick(View v) {
         //       Img1.getDrawable().setAlpha(128);
           //     like.add(new LikedAreas(DestName, ImgId));
              //  Log.d("image id = ", String.valueOf(ImgId));
            //}

        //});


        //Main Destination Page Item for Each Destination Like Naran,Hunza, Murree, Swat
        mdata.add(new

                DestinationM("Restaurants", R.drawable.restaurant));
        mdata.add(new

                DestinationM("Attraction Points", R.drawable.attraction_point));
        mdata.add(new

                DestinationM("Hotels", R.drawable.hotel));
        mdata.add(new

                DestinationM("Petrol Pump", R.drawable.petrol_pump));

        RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewDestinationMainPage);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(rcv.getContext(), mdata, this);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new

                GridLayoutManager(this, 2));
    }

    //onclick on recycler view of main destination page
    @Override
    public void onNoteClick(int position) {

        //get data from main destination page like attraction point, petrol pump etc
        DestinationM Info = mdata.get(position);
        String NameDestM = Info.getName();
        int Img = getImgId();
        String ImgDestM = String.valueOf(Img);
        String DestName = getDestName();

        if (NameDestM.equals("Attraction Points")) {

            Intent intent = new Intent(getApplicationContext(), AllAttractionPoint.class);
            intent.putExtra("Name", DestName);
            intent.putExtra("ImgId", ImgDestM);
            startActivityForResult(intent, 1);

        } else if (NameDestM.equals("Hotels")) {

            Intent intent = new Intent(getApplicationContext(), AllHotels.class);
            intent.putExtra("Name", DestName);
            intent.putExtra("ImgId", ImgDestM);
            startActivityForResult(intent, 1);

        } else if (NameDestM.equals("Petrol Pump")) {

            Intent intent = new Intent(getApplicationContext(), AllPetrolPump.class);
            intent.putExtra("Name", DestName);
            intent.putExtra("ImgId", ImgDestM);
            startActivityForResult(intent, 1);
        } else if (NameDestM.equals("Restaurants")) {

            Intent intent = new Intent(getApplicationContext(), AllRestaurant.class);
            intent.putExtra("Name", DestName);
            intent.putExtra("ImgId", ImgDestM);
            startActivityForResult(intent, 1);
        }


    }


}
