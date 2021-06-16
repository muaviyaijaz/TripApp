package com.example.tripapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentLikedVideo extends Fragment {


    DestinationPage obj = new DestinationPage();
    private RecyclerView rcv;
    List<LikedAreas> mdata = new ArrayList<>();
    View root;
    RecyclerViewAdapterAllLikedVideo adapter;

    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference myRef;
    FirebaseUser user;
    String uid;
    List<LikedAreasAttributes> LikedList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_view_all_liked_destination, container, false);

        getActivity().setTitle("Liked Destinations");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();
        myRef = mFirebaseDatabase.getReference().child("Liked Areas").child(uid);





//        rcv = (RecyclerView) root.findViewById(R.id.RecyclerViewAllLikdedVideo);
//        adapter = new RecyclerViewAdapterAllLikedVideo(this.getContext(), mdata);
//        rcv.setLayoutManager(new LinearLayoutManager(root.getContext()));
//        rcv.setAdapter(adapter);

            //Jo user login hai uske liked video firebase sai uthane hai aur idhr show krane

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                rcv = (RecyclerView) root.findViewById(R.id.RecyclerViewAllLikdedVideo);
                adapter = new RecyclerViewAdapterAllLikedVideo(getContext(), mdata);
                rcv.setLayoutManager(new LinearLayoutManager(root.getContext()));
                rcv.setAdapter(adapter);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LikedAreasAttributes Liked = new LikedAreasAttributes();
                    Liked = ds.getValue(LikedAreasAttributes.class);
                    Liked.setUID(ds.getKey());

                    LikedList.add(Liked);
                    mdata.add(new LikedAreas(LikedList.get(i).getName(),Integer.valueOf(LikedList.get(i).getImgId())));
                            i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Each Attraction", "Failed.........");
            }
        });
     //   mdata.add(new LikedAreas("Naran", R.drawable.naran));
//        mdata.add(new LikedVideo("Hunza",R.drawable.hunza));
//        mdata.add(new LikedVideo("Swat", R.drawable.swat));
//        mdata.add(new LikedVideo("Murree",R.drawable.murree));


        return root;

    }


}
