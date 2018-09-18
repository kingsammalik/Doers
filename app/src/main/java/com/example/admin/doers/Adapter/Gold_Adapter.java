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
import com.example.admin.doers.R;

public class Gold_Adapter extends RecyclerView.Adapter<Gold_Adapter.GoldModelViewHolder> {

    private Context context;
    private Gold_model[] data;

    public Gold_Adapter(Context context, Gold_model[] data) {
        this.context=context;
        this.data=data;

    }

    @Override
    public GoldModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.gold_custom,parent,false);
        return new GoldModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GoldModelViewHolder holder, int position) {
        Gold_model user= data[position];
        Glide.with(holder.imguser.getContext()).load(user.getImagelocation()).placeholder(R.drawable.doer300).into(holder.imguser);
        holder.textView.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public  class  GoldModelViewHolder  extends RecyclerView.ViewHolder{
        ImageView imguser;
        TextView textView;
        public GoldModelViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.personName);
            imguser=(ImageView)itemView.findViewById(R.id.imgGold);
        }
    }


}
