package com.example.admin.doers.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.doers.Fragments.Login_Fragment;
import com.example.admin.doers.Fragments.Register_Fragment;

public class PagerMainAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public PagerMainAdapter(FragmentManager fm, int pos) {
        super(fm);
        this.tabCount = pos;

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Login_Fragment login_fragment = new Login_Fragment();
                return login_fragment;
            case 1:
                Register_Fragment register_fragment = new Register_Fragment();
                return register_fragment;

            default:
                return null;
        }
    }
        @Override
        public int getCount () {
            return tabCount;
        }
    }

