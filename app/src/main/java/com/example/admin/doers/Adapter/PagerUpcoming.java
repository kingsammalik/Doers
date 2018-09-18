package com.example.admin.doers.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.doers.Model.Upcoming;
import com.example.admin.doers.R;
import com.example.admin.doers.VolleySingelton.MySingelton;
import com.squareup.picasso.Picasso;

import java.util.List;

public  class PagerUpcoming extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Upcoming> sliderImg;
    private ImageLoader imageLoader;


    public PagerUpcoming(List sliderImg, Context context) {
        this.sliderImg = sliderImg;
        this.context = context;
    }

    @Override
    public int getCount() {

        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_upcoming, null);

        Upcoming utils = sliderImg.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.imgupcoming);

        imageLoader = MySingelton.getInstance(context).getImageLoader();

        Picasso.with(context).load(sliderImg.get(position).getSliderImageUrl()).placeholder(R.drawable.doer300).into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}