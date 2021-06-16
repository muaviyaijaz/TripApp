package com.example.tripapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;

public class EachAttractionPoint extends AppCompatActivity {

    FusedLocationProviderClient Client;
    String Loc;
    boolean f = true;

    TextView Category1;
    TextView Parking1;
    TextView FamilyFriendly1;
    TextView Network1;
    TextView TuckShop1;
    TextView About1;
    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference MyLikedReference;
    DatabaseReference MyLikedCheckRef;
    List<AttractionPointAttributes> AttList = new ArrayList<>();
    List<LikedAreasAttributes> LikedList;
    private FirebaseAnalytics mFirebaseAnalytics;

    String AttName;
    String AttImgId;

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
        setContentView(R.layout.view_each_attraction_point_detail);
        //CheckUserHasLiked();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //Name = Altit Fort, Img = Image, DestName = Hunza
        AttName = getIntent().getStringExtra("Name");
        AttImgId = getIntent().getStringExtra("ImgID");
        String DestName = getIntent().getStringExtra("DestinationName");


        Liked_Thumbs_Up = findViewById(R.id.liked_image_thumb);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Attraction Point");
        myRef.keepSynced(true);
        user = mAuth.getCurrentUser();
        uid = user.getUid();


        //get all detail of relevant attraction point.....
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    AttractionPointAttributes Attract = new AttractionPointAttributes();
                    Attract = ds.getValue(AttractionPointAttributes.class);
                    Attract.setUID(ds.getKey());

                    AttList.add(Attract);
                }
                SetDetails(AttName);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Each Attraction", "Failed.........");
            }
        });


        //-------------------------------------------------------------------------------------------------//
        //-------------------------------------------------------------------------------------------------//


        //testing if the user has liked the destination already

        MyLikedCheckRef = mFirebaseDatabase.getReference().child("Liked Areas").child(uid);
        MyLikedCheckRef.keepSynced(true);
        MyLikedCheckRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Toast.makeText(getApplicationContext(), "OnDataChanged Called Already Liked", Toast.LENGTH_SHORT).show();

                LikedList = new ArrayList<>();
                int i = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LikedAreasAttributes Liked = new LikedAreasAttributes();
                    Liked = ds.getValue(LikedAreasAttributes.class);
                    Liked.setUID(ds.getKey());
                    LikedList.add(Liked);
                    i++;
                }
                String s = String.valueOf(LikedList.size());
                Toast.makeText(getApplicationContext(), "Liked Destination Already  " + s, Toast.LENGTH_SHORT).show();
                if (i != 0) {
                    for (int j = 0; j < LikedList.size(); j++) {
                        if (LikedList.get(j).getName().equals(AttName)) {
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

        ///testing complete

        //set the image and text of selected attraction point with name and Img
        FrameLayout fr = (FrameLayout) findViewById(R.id.frameLayoutatt);
        TextView tx = (TextView) findViewById(R.id.TextViewEachAttPoints);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fr.setBackground(getDrawable(Integer.valueOf(AttImgId)));
        }
        tx.setText(AttName);

        final String Destination = AttName;

        //get current location code
        Client = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if (ActivityCompat.checkSelfPermission(EachAttractionPoint.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            getCurrentLocation();

        } else {
            ActivityCompat.requestPermissions(EachAttractionPoint.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        final Bundle params = new Bundle();

        Button bn = (Button) findViewById(R.id.EachAttractionDirection);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayTrack(getLoc(), Destination);
                params.putString("ID",user.getUid());
                params.putString("Email",user.getEmail());
                params.putString("Destination",Destination);
                mFirebaseAnalytics.logEvent("Destinations",params);

            }
        });


    }

    public void SetDetails(String AttName) {


        String About = null;
        String Category = null;
        String Family_Friendly = null;
        String Name = null;
        String Network = null;
        String Parking = null;
        String Tuck_Shop = null;


        for (AttractionPointAttributes att : AttList) {
            if (att.getUID().contentEquals(AttName)) {
                About = att.getAbout();
                Network = att.getNetwork();
                Category = att.getCategory();
                Family_Friendly = att.getFamily_Friendly();
                Parking = att.getParking();
                Tuck_Shop = att.getTuck_Shop();
                Name = att.getName();
            }
        }

        //set all fields

        Category1 = (TextView) findViewById(R.id.Category);
        Category1.setText("    •      " + Category);

        About1 = (TextView) findViewById(R.id.About);
        About1.setText(About);

        FamilyFriendly1 = (TextView) findViewById(R.id.FamilyFriendlyTextView);
        FamilyFriendly1.setText(Family_Friendly);
        if (Family_Friendly.equals("Yes"))
            FamilyFriendly1.setTextColor(Color.parseColor("#81c639")); //green
        else if (Family_Friendly.equals("No"))
            FamilyFriendly1.setTextColor(Color.parseColor("#FF0000")); //Red
        else if (Family_Friendly.equals("Limited"))
            FamilyFriendly1.setTextColor(Color.parseColor("#FFFF00")); //yellow

        Parking1 = (TextView) findViewById(R.id.ParkingTextView);
        Parking1.setText(Parking);
        if (Parking.equals("Yes"))
            Parking1.setTextColor(Color.parseColor("#81c639")); //green
        else if (Parking.equals("No"))
            Parking1.setTextColor(Color.parseColor("#FF0000")); //Red
        else if (Parking.equals("Limited"))
            Parking1.setTextColor(Color.parseColor("#FFFF00")); //yellow

        TuckShop1 = (TextView) findViewById(R.id.TuckShopTextView);
        TuckShop1.setText(Tuck_Shop);
        if (Tuck_Shop.equals("Yes"))
            TuckShop1.setTextColor(Color.parseColor("#81c639")); //green
        else if (Tuck_Shop.equals("No"))
            TuckShop1.setTextColor(Color.parseColor("#FF0000")); //Red
        else if (Tuck_Shop.equals("Limited"))
            TuckShop1.setTextColor(Color.parseColor("#FFFF00")); //yellow

        Network1 = (TextView) findViewById(R.id.NetworkTextView);
        Network1.setText(Network);
        if (Network.equals("Yes"))
            Network1.setTextColor(Color.parseColor("#81c639")); //green
        else if (Network.equals("No"))
            Network1.setTextColor(Color.parseColor("#FF0000")); //Red
        else if (Network.equals("Limited"))
            Network1.setTextColor(Color.parseColor("#FFFF00")); //yellow


    }

    public void CheckUserHasLiked() {

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }


    public void ImageLikedButtonPressed(View view) {
        if (checked == false) {
            Liked_Thumbs_Up.setColorFilter(Color.parseColor("#FF0000")); //red
            Toast.makeText(getApplicationContext(), "whiteeeeee to red", Toast.LENGTH_SHORT).show();
            MyLikedReference = mFirebaseDatabase.getReference().child("Liked Areas").child(uid);
            MyLikedReference.keepSynced(true);
            MyLikedReference.push().setValue(new LikedAreasAttributes(AttName, AttImgId, uid));
            Toast.makeText(getApplicationContext(), "Successfully Liked", Toast.LENGTH_SHORT).show();
            checked = false;
        } else if (checked == true) //red
        {
            checked = false;
            f = true;
            Liked_Thumbs_Up.setColorFilter(Color.parseColor("#FFFFFF"));
            MyLikedReference = mFirebaseDatabase.getReference().child("Liked Areas").child(uid);
            MyLikedReference.keepSynced(true);
            MyLikedReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Toast.makeText(getApplicationContext(), "On Data Change Called Removed", Toast.LENGTH_SHORT).show();
                    int i = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (f == true) {
                            LikedAreasAttributes Liked = new LikedAreasAttributes();
                            Liked = ds.getValue(LikedAreasAttributes.class);
                            Liked.setUID(ds.getKey());
                            Liked.setName(AttName);
                            Liked.setImgId(AttImgId);

                            if (Liked.getName().equals(LikedList.get(i).getName())) {
                                Toast.makeText(getApplicationContext(),Liked.getName() + LikedList.get(i).getName(),Toast.LENGTH_LONG).show();;
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
