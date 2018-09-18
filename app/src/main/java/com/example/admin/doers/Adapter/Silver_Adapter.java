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
import com.example.admin.doers.Model.Silver_model;
import com.example.admin.doers.R;

public class Silver_Adapter extends RecyclerView.Adapter<Silver_Adapter.SilverModelViewHolder>  {

    private Context context;
    private Silver_model[] data;
    private boolean isEmpty;

    public Silver_Adapter(Context context, Silver_model[] data,boolean isEmpty) {
        this.context=context;
        this.data=data;
        this.isEmpty=isEmpty;
    }

    public Silver_Adapter(boolean isEmpty) {
        this.isEmpty=isEmpty;
    }

    @Override
    public Silver_Adapter.SilverModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.gold_custom,parent,false);
        return new Silver_Adapter.SilverModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Silver_Adapter.SilverModelViewHolder holder, int position) {
        if (!isEmpty){
            Silver_model user= data[position];
            Glide.with(holder.imguser.getContext()).load(user.getImagelocation()).into(holder.imguser);
            holder.textView.setText(user.getName());
        }

    }

    @Override
    public int getItemCount() {
        if (isEmpty)
            return 0;
        else
        return data.length;
    }

    public  class  SilverModelViewHolder  extends RecyclerView.ViewHolder{
        ImageView imguser;
        TextView textView;
        public SilverModelViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.personName);
            imguser=(ImageView)itemView.findViewById(R.id.imgGold);

        }
    }



}
