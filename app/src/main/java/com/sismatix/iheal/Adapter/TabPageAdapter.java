package com.sismatix.iheal.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.sismatix.iheal.Fragments.Tablayout_hair_fregment;

/**
 * Created by ap6 on 29/9/18.
 */

public class TabPageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public TabPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Tablayout_hair_fregment tab1 = new Tablayout_hair_fregment();
                return tab1;
            case 1:
                Tablayout_hair_fregment tab2 = new Tablayout_hair_fregment();

                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}