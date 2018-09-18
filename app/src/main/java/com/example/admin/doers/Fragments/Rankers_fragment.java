package com.example.admin.doers.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.doers.Adapter.PagerRankerAdapter;
import com.example.admin.doers.R;


public class Rankers_fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_rankers_fragment, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Silver"));
        tabLayout.addTab(tabLayout.newTab().setText("Gold"));
        tabLayout.addTab(tabLayout.newTab().setText("Pearl"));
        tabLayout.addTab(tabLayout.newTab().setText("Ruby"));
        tabLayout.addTab(tabLayout.newTab().setText("Emerald"));
        tabLayout.addTab(tabLayout.newTab().setText("Topaz"));
        tabLayout.addTab(tabLayout.newTab().setText("Diamond"));

        viewPager = (ViewPager) view.findViewById(R.id.rankerpager);
        PagerRankerAdapter pagerRankerAdapter=new PagerRankerAdapter(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerRankerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }


}
