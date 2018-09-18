package com.example.admin.doers.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.doers.Fragments.Diamond;
import com.example.admin.doers.Fragments.Emerald;
import com.example.admin.doers.Fragments.Gold;
import com.example.admin.doers.Fragments.Login_Fragment;
import com.example.admin.doers.Fragments.Peral;
import com.example.admin.doers.Fragments.Register_Fragment;
import com.example.admin.doers.Fragments.Ruby;
import com.example.admin.doers.Fragments.Silver;
import com.example.admin.doers.Fragments.Topaz;

public class PagerRankerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public PagerRankerAdapter(FragmentManager fm,int pos) {
        super(fm);
        this.tabCount = pos;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Gold gold = new Gold();
                return gold;
            case 1:
                Silver silver = new Silver();
                return silver;
            case 2:
                Peral peral = new Peral();
                return peral;
            case 3:
                Ruby ruby = new Ruby();
                return ruby;
            case 4:
                Emerald emerald = new Emerald();
                return emerald;
            case 5:
                Topaz topaz = new Topaz();
                return topaz;

            case 6:
                Diamond diamond = new Diamond();
                return diamond;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
