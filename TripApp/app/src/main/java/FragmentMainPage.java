package com.example.tripapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class FragmentMainPage extends Fragment {

    ArrayList<String> upcoming_names = new ArrayList<>();
    ListView upcoming_list_view;
    SearchView searchView;

    private String[] Name = {"Naran","Hunza","Swat","Murree"};
    private int[] ImgId={R.drawable.naran,R.drawable.hunza,R.drawable.swat,R.drawable.murree};

    String SearchDestName;
    int SearchDestImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_main_page, container, false);

        getActivity().setTitle("Home");

        ViewPager viewPager = root.findViewById(R.id.pager);
        ImageAdapter adapter = new ImageAdapter(this.getContext());
        viewPager.setAdapter(adapter);

        upcoming_names.add("Skardu");
        upcoming_names.add("Nathia Gali");
        upcoming_names.add("Naran");

        upcoming_list_view = (ListView) root.findViewById(R.id.UpcomingList);
        Upcoming_adapter adapter2 = new Upcoming_adapter(this.getActivity(), upcoming_names);
        upcoming_list_view.setAdapter(adapter2);


        Button dest = root.findViewById(R.id.AllDestinationButton);
        dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllDestinations.class);
                startActivityForResult(intent, 1);
            }
        });

        //search bar
        searchView = (SearchView) root.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchDestName=query;
                SearchDestImg=ReturnImageId(query);

                if(SearchDestImg==0){
                    Toast.makeText(getContext(),"Not Found",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getContext(),DestinationPage.class);
                    intent.putExtra("SearchName",SearchDestName);
                    intent.putExtra("SearchImage",String.valueOf(SearchDestImg));
                    startActivity(intent);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return root;
    }

    public int ReturnImageId(String name){
        int id=0;

        for(int i=0;i<Name.length;i++){
            if(name.equals(Name[i])){
                id=ImgId[i];
                break;
            }
        }

        return id;
    }


}
