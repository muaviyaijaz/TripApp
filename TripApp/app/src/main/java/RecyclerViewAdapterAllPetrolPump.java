package com.example.tripapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterAllPetrolPump extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Context context;
    List<PetrolPump> mData = new ArrayList<>();

    public RecyclerViewAdapterAllPetrolPump(Context context, List<PetrolPump> mData) {
        this.context = context;
        this.mData = mData;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view= mInflater.inflate(R.layout.card_view_all_petrolpump,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder vh1 = (MyViewHolder) holder;
        vh1.Name.setText(mData.get(position).getName());
        vh1.img_book_Thumbnail.setImageResource(mData.get(position).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name;
        ImageView img_book_Thumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.TextIconpetrolpump);
            img_book_Thumbnail = (ImageView) itemView.findViewById(R.id.IconImgpetrolpump);
        }
    }


}
