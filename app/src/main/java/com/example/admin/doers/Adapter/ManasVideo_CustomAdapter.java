package com.example.admin.doers.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.doers.Model.Latest_videos_model;
import com.example.admin.doers.R;

public class ManasVideo_CustomAdapter extends RecyclerView.Adapter<ManasVideo_CustomAdapter.MyViewHolder> {
    Context context;
    Latest_videos_model[] videos_models;

    public ManasVideo_CustomAdapter(Context context, Latest_videos_model[] videos_models) {
        this.context = context;
        this.videos_models = videos_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.manasvideo_custom,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Latest_videos_model user= videos_models[position];
        Glide.with(holder.imageView1.getContext()).load(user.getThumbnail()).into(holder.imageView1);
        holder.imageView2.setImageResource(R.drawable.youtubeicon2);
        }

    @Override
    public int getItemCount() {
        return videos_models.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView1, imageView2;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.imgGold);
            imageView2 = itemView.findViewById(R.id.imgGold2);
        }
    }
}
