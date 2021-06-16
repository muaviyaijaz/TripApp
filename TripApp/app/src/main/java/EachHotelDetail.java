package com.example.tripapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EachHotelDetail extends AppCompatActivity {

    FusedLocationProviderClient Client;
    String Loc;
    boolean f = true;

    TextView Contact_No1;
    TextView Heater1;
    TextView Laundry1;
    TextView Name1;
    TextView Parking1;
    TextView Petrol_Pump1;
    TextView Restaurant1;
    TextView Standard1;
    TextView Transportation1;
    private FirebaseAnalytics mFirebaseAnalytics;


    ImageView Heater2;
    ImageView Laundry2;
    ImageView Parking2;
    ImageView Petrol_Pump2;
    ImageView Restaurant2;
    ImageView Transportation2;


    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference MyLikedReference;
    DatabaseReference MyLikedCheckRef;
    List<HotelAttributes> HotelList = new ArrayList<>();
    List<LikedHotelAttributes> LikedList;

    String HotelName;
    String HotImgId;

    ImageView Liked_Thumbs_Up;
    String uid;
    FirebaseUser user;

    boolean checked = false;



    public String getLoc() {
        return Loc;
    }

    public void setLoc(String loc) {
        this.Loc = loc;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_each_hotel_detail);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        HotelName = getIntent().getStringExtra("Name");
        HotImgId = getIntent().getStringExtra("ImgID");
        String DestName = getIntent().getStringExtra("DestinationName");

        Liked_Thumbs_Up = findViewById(R.id.hotel_liked_thumb);


        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Hotel");
        myRef.keepSynced(true);
        user = mAuth.getCurrentUser();
        uid = user.getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HotelAttributes Hotel = new HotelAttributes();
                    Hotel = ds.getValue(HotelAttributes.class);
                    Hotel.setUID(ds.getKey());

                    HotelList.add(Hotel);
                }
                SetDetails(HotelName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Each Attraction","Failed.........");
            }
        });

        //testing if the user has liked the hotel already

        MyLikedCheckRef = mFirebaseDatabase.getReference().child("Liked Hotels").child(uid);
        MyLikedCheckRef.keepSynced(true);
        MyLikedCheckRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Toast.makeText(getApplicationContext(), "OnDataChanged Called Already Liked", Toast.LENGTH_SHORT).show();

                LikedList = new ArrayList<>();
                int i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LikedHotelAttributes Liked = new LikedHotelAttributes();
                    Liked = ds.getValue(LikedHotelAttributes.class);
                    Liked.setUID(ds.getKey());
                    LikedList.add(Liked);
                    i++;
                }
                String s = String.valueOf(LikedList.size());
                //Toast.makeText(getApplicationContext(), "Liked Destination Already  " + s, Toast.LENGTH_SHORT).show();
                if (i != 0) {
                    for (int j = 0; j < LikedList.size(); j++) {
                        if (LikedList.get(j).getHotelName().equals(HotelName)) {
                            checked = true;
                            Liked_Thumbs_Up.setColorFilter(Color.parseColor("#FF0000"));//red
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Each Attraction", "Failed.........");
            }
        });



        //set the image and text of selected attraction point with name and Img
        FrameLayout fr = (FrameLayout) findViewById(R.id.frameLayoutEachHotel);
        TextView tx = (TextView) findViewById(R.id.EachHotelName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fr.setBackground(getDrawable(Integer.valueOf(HotImgId)));
        }
        tx.setText( HotelName);

        final String Destination =  HotelName;

        //get current location code
        Client = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if (ActivityCompat.checkSelfPermission(EachHotelDetail.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            getCurrentLocation();

        } else {
            ActivityCompat.requestPermissions(EachHotelDetail.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        final Bundle params = new Bundle();
        Button bn = (Button) findViewById(R.id.DirectionEachHotel);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayTrack(getLoc(), Destination);
                params.putString("ID",user.getUid());
                params.putString("Email",user.getEmail());
                params.putString("Destination",Destination);
                mFirebaseAnalytics.logEvent("HotelDestinations",params);
            }
        });


    }

    public void SetDetails(String HotelName) {
        String Contact_No=null;
        String Heater=null;
        String Laundry=null;
        String Name=null;
        String Parking=null;
        String Petrol_Pump=null;
        String Restaurant=null;
        String Standard=null;
        String Transportation=null;

        for(HotelAttributes hott : HotelList){
            if(hott.getUID().contentEquals( HotelName)){
                Contact_No=hott.getContact_No();
                Heater = hott.getHeater();
                Laundry=hott.getLaundry();
                Parking=hott.getParking();
                Petrol_Pump=hott.getPetrol_pump();
                Restaurant=hott.getRestaurant();
                Standard=hott.getStandard();
                Transportation=hott.getTransportation();
                Name = hott.getName();
            }
        }

        //set all fields

        Contact_No1 = (TextView) findViewById(R.id.phone_num_text);
        Contact_No1.setText("    •      " + Contact_No);

        Heater1 = (TextView) findViewById(R.id.heater_text);
        Heater2 = (ImageView) findViewById(R.id.heater_image);

        if(Heater.equals("No"))
        {
            Heater1.setTextColor(Color.parseColor("#FFFF00")); //yellow
            Heater1.setTypeface(Typeface.DEFAULT_BOLD);
            Heater1.setAlpha((float) 0.3);
            Heater2.setAlpha((float) 0.3);
        }
        else
        {
            Heater1.setTextColor(Color.parseColor("#000000")); //black
            Heater1.setTypeface(Typeface.DEFAULT_BOLD);
        }




        Laundry1=(TextView) findViewById(R.id.laundry_text);
        Laundry2=(ImageView) findViewById(R.id.laundry_image);
        if(Laundry.equals("No"))
        {
            Laundry1.setTextColor(Color.parseColor("#FFFF00")); //yellow
            Laundry1.setTypeface(Typeface.DEFAULT_BOLD);
            Laundry1.setAlpha((float) 0.3);
            Laundry2.setAlpha((float) 0.3);
        }
        else
        {
            Laundry1.setTextColor(Color.parseColor("#000000")); //black
            Laundry1.setTypeface(Typeface.DEFAULT_BOLD);
        }



        Parking1=(TextView) findViewById(R.id.parking_text);
        Parking2=(ImageView) findViewById(R.id.parking_image);

        if(Parking.equals("No"))
        {
            Parking1.setTextColor(Color.parseColor("#FFFF00")); //yellow
            Parking1.setTypeface(Typeface.DEFAULT_BOLD);
            Parking1.setAlpha((float) 0.3);
            Parking2.setAlpha((float) 0.3);
        }
        else
        {
            Parking1.setTextColor(Color.parseColor("#000000")); //black
            Parking1.setTypeface(Typeface.DEFAULT_BOLD);
        }




        Petrol_Pump1=(TextView) findViewById(R.id.petrol_pump_text);
        Petrol_Pump2=(ImageView) findViewById(R.id.petrol_pump_image);

        if(Petrol_Pump.equals("No"))
        {
            Petrol_Pump1.setTextColor(Color.parseColor("#FFFF00")); //yellow
            Petrol_Pump1.setTypeface(Typeface.DEFAULT_BOLD);
            Petrol_Pump1.setAlpha((float) 0.3);
            Petrol_Pump2.setAlpha((float) 0.3);
        }
        else
        {
            Petrol_Pump1.setTextColor(Color.parseColor("#000000")); //black
            Petrol_Pump1.setTypeface(Typeface.DEFAULT_BOLD);
        }


        Restaurant1 =(TextView) findViewById(R.id.restaurant_text);
        Restaurant2 =(ImageView) findViewById(R.id.restaurant_image);
        if(Restaurant.equals("No"))
        {
            Restaurant1.setTextColor(Color.parseColor("#FFFF00")); //yellow
            Restaurant1.setTypeface(Typeface.DEFAULT_BOLD);
            Restaurant1.setAlpha((float) 0.3);
            Restaurant2.setAlpha((float) 0.3);
        }
        else
        {
            Restaurant1.setTextColor(Color.parseColor("#000000")); //black
            Restaurant1.setTypeface(Typeface.DEFAULT_BOLD);
        }

        Standard1 = (TextView) findViewById(R.id.standard);
        Standard1.setText(Standard);

        Transportation1 = (TextView) findViewById(R.id.transportation_text);
        Transportation2 = (ImageView) findViewById(R.id.transportation_image);
        if(Transportation.equals("No"))
        {
            Transportation1.setTextColor(Color.parseColor("#FFFF00")); //yellow
            Transportation1.setTypeface(Typeface.DEFAULT_BOLD);
            Transportation1.setAlpha((float) 0.3);
            Transportation2.setAlpha((float) 0.3);
        }
        else
        {
            Transportation1.setTextColor(Color.parseColor("#000000")); //black
            Transportation1.setTypeface(Typeface.DEFAULT_BOLD);
        }

    }


    public void HotelLikedButtonPressed(View view) {
        if (checked == false) {
            Liked_Thumbs_Up.setColorFilter(Color.parseColor("#FF0000")); //red
           // Toast.makeText(getApplicationContext(), "whiteeeeee to red", Toast.LENGTH_SHORT).show();
            MyLikedReference = mFirebaseDatabase.getReference().child("Liked Hotels").child(uid);
            MyLikedCheckRef.keepSynced(true);
            MyLikedReference.push().setValue(new LikedHotelAttributes(HotelName, HotImgId, uid));
            //Toast.makeText(getApplicationContext(), "Successfully Liked", Toast.LENGTH_SHORT).show();
            checked = false;
        } else if (checked == true) //red
        {
            checked = false;
            f = true;
            Liked_Thumbs_Up.setColorFilter(Color.parseColor("#FFFFFF"));
            MyLikedReference = mFirebaseDatabase.getReference().child("Liked Hotels").child(uid);
            MyLikedReference.keepSynced(true);
            MyLikedReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Toast.makeText(getApplicationContext(), "On Data Change Called Removed", Toast.LENGTH_SHORT).show();
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (f == true) {
                            LikedAreasAttributes Liked = new LikedAreasAttributes();
                            Liked = ds.getValue(LikedAreasAttributes.class);
                            Liked.setUID(ds.getKey());
                            Liked.setName(HotelName);
                            Liked.setImgId(HotImgId);

                            if (Liked.getName().equals(LikedList.get(i).getHotelName())) {
                            //    Toast.makeText(getApplicationContext(),Liked.getName() + LikedList.get(i).getHotelName(),Toast.LENGTH_LONG).show();;
                                LikedList.remove(i);
                                ds.getRef().removeValue();
                                f = false;

                            }

                        }
                        else {

                        }
                        i++;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("Each Attraction", "Failed.........");
                }
            });


        }
    }

    private void getCurrentLocation() {
        //initialize task location

        Task<Location> task = Client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    String L = String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude());
                    Loc = String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude());
                    setLoc(Loc);
                    Log.d("Location hammad ", getLoc());


                }
            }
        });

    }

    private void DisplayTrack(String source, String destination) {

        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + source + "/" + destination);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }
}
