package com.example.tripapp;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterAllLikedVideo extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Context context;
    List<LikedAreas> mData = new ArrayList<>();

    public RecyclerViewAdapterAllLikedVideo(Context context, List<LikedAreas> mData) {
        this.context = context;
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view= mInflater.inflate(R.layout.card_view_all_liked_video,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder vh1 = (MyViewHolder) holder;
        vh1.Name.setText(mData.get(position).getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            vh1.img_book_Thumbnail.setBackground(context.getApplicationContext().getDrawable(mData.get(position).getThumbnail()));
        }
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
            Name = (TextView) itemView.findViewById(R.id.TextIconlikedvideo);
            img_book_Thumbnail = (ImageView) itemView.findViewById(R.id.IconImglikedvideo);
        }
    }


}
