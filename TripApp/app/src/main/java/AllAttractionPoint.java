package com.example.tripapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllAttractionPoint extends AppCompatActivity implements RecyclerViewAdapterAllAttractionPoints.OnNoteListener {

    //Attraction points for Naran Side
    private String[] NaranAttractionPointsName = new String[]{"Lulusar Lake", "Babusar Top", "Kewai Waterfall", "Shogran"};
    private int[] NaranAttractionPointsImg = new int[]{R.drawable.lulusar_lake, R.drawable.babusar_top, R.drawable.kewai_waterfall, R.drawable.shogran};

    //Attraction points for Hunza side
    private String[] HunzaAttractionPointsName = new String[]{"Khunjerab Pass", "Attabad Lake", "Baltit Fort", "Altit Fort", "Karakoram Highway"};
    private int[] HunzaAttractionPointsImg = new int[]{R.drawable.khunjerab_pass, R.drawable.attabad_lake, R.drawable.baltit_fort, R.drawable.altit_fort, R.drawable.karakoram_highway};

    //Attraction points for Murree side
    private String[] MurreeAttractionPointsName = new String[]{"Dunga Gali", "Patriata", "Nathia Gali"};
    private int[] MurreeAttractionPointsImg = new int[]{R.drawable.dunga_gali, R.drawable.patriata, R.drawable.nathia_gali};

    //Attraction points for Swat side
    private String[] SwatAttractionPointsName = new String[]{"Kumrat Valley", "Mahodand Lake", "Malam Jabba", "Kundol Lake"};
    private int[] SwatAttractionPointsImg = new int[]{R.drawable.kumrat_valley, R.drawable.mahodand_lake, R.drawable.malam_jabba, R.drawable.kundol_lake};

    //Recycler view list of attraction points
    private List<AttractionPoints> mdata = new ArrayList<>();

    String Refname;

    public void setName(String name) {
        this.Refname = name;
    }

    public String getName() {
        return Refname;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_attractionpoint);

        //Get DestName and Img id from previous activity
        String name = getIntent().getStringExtra("Name");
        String id = getIntent().getStringExtra("ImgId");
        int Img = Integer.valueOf(id);

        //store Refname
        setName(name);

        TextView Name = (TextView) findViewById(R.id.textviewattpoint);
        FrameLayout ImgFrame = (FrameLayout) findViewById(R.id.frameLayoutattpoint);

        //set the name of attraction point
        Name.setText(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ImgFrame.setBackground(getDrawable(Img));
        }

        if (name.equals("Naran")) {

            //if selected item is naran from main destination page then copy its relevenat attrtaction points
            for (int i = 0; i < NaranAttractionPointsName.length; i++) {
                AttractionPoints obj = new AttractionPoints(NaranAttractionPointsName[i], NaranAttractionPointsImg[i]);
                mdata.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewattpoint);
            RecyclerViewAdapterAllAttractionPoints adapter = new RecyclerViewAdapterAllAttractionPoints(this, mdata, this);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));
        } else if (name.equals("Hunza")) {

            //if selected item is Hunza from main destination page then copy its relevenat attrtaction points
            for (int i = 0; i < HunzaAttractionPointsName.length; i++) {
                AttractionPoints obj = new AttractionPoints(HunzaAttractionPointsName[i], HunzaAttractionPointsImg[i]);
                mdata.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewattpoint);
            RecyclerViewAdapterAllAttractionPoints adapter = new RecyclerViewAdapterAllAttractionPoints(this, mdata, this);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));


        } else if (name.equals("Murree")) {

            //if selected item is Murree from main destination page then copy its relevenat attrtaction points
            for (int i = 0; i < MurreeAttractionPointsName.length; i++) {
                AttractionPoints obj = new AttractionPoints(MurreeAttractionPointsName[i], MurreeAttractionPointsImg[i]);
                mdata.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewattpoint);
            RecyclerViewAdapterAllAttractionPoints adapter = new RecyclerViewAdapterAllAttractionPoints(this, mdata, this);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));

        } else if (name.equals("Swat")) {

            //if selected item is Swat from main destination page then copy its relevenat attrtaction points
            for (int i = 0; i < SwatAttractionPointsName.length; i++) {
                AttractionPoints obj = new AttractionPoints(SwatAttractionPointsName[i], SwatAttractionPointsImg[i]);
                mdata.add(obj);
            }

            //Recycler View for all attraction point details
            RecyclerView rcv = (RecyclerView) findViewById(R.id.RecyclerViewattpoint);
            RecyclerViewAdapterAllAttractionPoints adapter = new RecyclerViewAdapterAllAttractionPoints(this, mdata, this);
            rcv.setAdapter(adapter);
            rcv.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    @Override
    public void onNoteClick(int position) {

        String DName = getName();
        AttractionPoints obj;

        if (DName.equals("Naran")) {
            obj = mdata.get(position);
            Intent intent= new Intent(getApplicationContext(),EachAttractionPoint.class);
            intent.putExtra("Name",obj.getName());
            intent.putExtra("ImgID",String.valueOf(obj.getThumbnail()));
            intent.putExtra("DestinationName","Naran");
            startActivityForResult(intent,1);

        } else if (DName.equals("Hunza")) {
            obj = mdata.get(position);
            Intent intent= new Intent(getApplicationContext(),EachAttractionPoint.class);
            intent.putExtra("Name",obj.getName());
            intent.putExtra("ImgID",String.valueOf(obj.getThumbnail()));
            intent.putExtra("DestinationName","Hunza");
            startActivityForResult(intent,1);


        } else if (DName.equals("Swat")) {
            obj = mdata.get(position);
            Intent intent= new Intent(getApplicationContext(),EachAttractionPoint.class);
            intent.putExtra("Name",obj.getName());
            intent.putExtra("ImgID",String.valueOf(obj.getThumbnail()));
            intent.putExtra("DestinationName","Swat");
            startActivityForResult(intent,1);


        } else if (DName.equals("Murree")) {
            obj = mdata.get(position);
            Intent intent= new Intent(getApplicationContext(),EachAttractionPoint.class);
            intent.putExtra("Name",obj.getName());
            intent.putExtra("ImgID",String.valueOf(obj.getThumbnail()));
            intent.putExtra("DestinationName","Murree");
            startActivityForResult(intent,1);


        }


    }


}
