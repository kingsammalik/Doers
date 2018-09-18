package com.example.admin.doers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.doers.Model.GetSet_Videos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashwani on 6/7/2016.
 */
public class video_adapter extends BaseAdapter {
    Context context;
    private List<GetSet_Videos> movies=new ArrayList<>();
    LayoutInflater layoutInflater;

    public video_adapter(Context context, List<GetSet_Videos> movies) {

        this.context=context;
        this.movies = movies;
        Log.e("constructor called","");

    }

    @Override
    public int getCount() {
        Log.d("-size", String.valueOf(movies.size()));
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row=view;
        if(row==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=  inflater.inflate(R.layout.manasvideo_custom,viewGroup,false);

        }
       else {

            ImageView imageView = (ImageView) row.findViewById(R.id.imgGold);
            ImageView imageView1=row.findViewById(R.id.imgGold2);
            imageView1.setImageResource(R.drawable.youtubeicon2);
            Picasso.with(context).load(movies.get(i).getThumbnail()).placeholder(R.drawable.doer300). resize(300,150).centerCrop().into(imageView);
        }
        return row;
    }
}
