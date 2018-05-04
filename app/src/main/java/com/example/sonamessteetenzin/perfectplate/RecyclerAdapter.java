package com.example.sonamessteetenzin.perfectplate;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Sonam ESSTEE Tenzin on 4/21/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    ArrayList<Album> arrayList = new ArrayList<>();
    Activity activity;

    public RecyclerAdapter(ArrayList<Album> arrayList, Context context)
    {
        this.arrayList= arrayList;
        activity = (Activity) context;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Title.setText(arrayList.get(position).getTitle());
        String path =Config.img_path+arrayList.get(position).getId()+".jpg";
        Glide.with(activity).load(path).into(holder.Thumbnail);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView Thumbnail;
        TextView Title;

        public MyViewHolder(View itemView) {
            super(itemView);

            Thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
            Title = (TextView)itemView.findViewById(R.id.album_title);

        }
    }
}
