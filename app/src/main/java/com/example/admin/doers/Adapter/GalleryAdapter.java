package com.example.admin.doers.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.doers.Model.Gallery_model;
import com.example.admin.doers.Model.Image;
import com.example.admin.doers.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context context;
    private Gallery_model data;
    private CallBack callBack;

    public GalleryAdapter(Context context, Gallery_model data,CallBack callBack) {

        this.context = context;
        this.data = data;
        this.callBack=callBack;
    }

    public interface CallBack{
        void onFavClicked(String url);
    }

    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.gallery_custom, parent, false);
        return new GalleryAdapter.GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.GalleryViewHolder holder, final int position) {

        Image user = data.getImages().get(position);
        Log.e("adapter", "url " + user.getImageLocation());
        Glide.with(holder.imguser.getContext()).load(user.getImageLocation()).placeholder(R.drawable.doer300).into(holder.imguser);
        holder.imageView2.setImageResource(R.drawable.ic_favorite_black_24dp);
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onFavClicked(data.getImages().get(holder.getAdapterPosition()).getImageLocation());
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.getImages().size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imguser, imageView2;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            imguser =  itemView.findViewById(R.id.imgGold);
            imageView2 = itemView.findViewById(R.id.imgGold2);
        }
    }

}