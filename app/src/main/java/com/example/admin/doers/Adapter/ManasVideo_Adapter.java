package com.example.admin.doers.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.doers.Model.Gold_model;
import com.example.admin.doers.Model.Latest_videos_model;
import com.example.admin.doers.R;

public class ManasVideo_Adapter extends RecyclerView.Adapter<ManasVideo_Adapter.ManasVideoViewHolder> {

    private Context context;
    private Latest_videos_model[] videos;

    public ManasVideo_Adapter(Context context, Latest_videos_model[] videos) {
        this.context=context;
        this.videos=videos;

    }

    @Override
    public ManasVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.gold_custom,parent,false);
        return new ManasVideo_Adapter.ManasVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ManasVideoViewHolder holder, int position) {
        Latest_videos_model user= videos[position];
        Glide.with(holder.imguser.getContext()).load(user.getThumbnail()).into(holder.imguser);

    }

    @Override
    public int getItemCount() {
        return videos.length;
    }

    public  class ManasVideoViewHolder extends RecyclerView.ViewHolder{
        ImageView imguser;
        TextView textView;
        public ManasVideoViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.personName);
            imguser=(ImageView)itemView.findViewById(R.id.imgGold);
        }
    }


}
