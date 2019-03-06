package com.sismatix.iheal.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sismatix.iheal.Fragments.Product_category_freg;
import com.sismatix.iheal.Fragments.Tablayout_hair_fregment;


public class Nature_TabPager_Adapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public Nature_TabPager_Adapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Product_category_freg tab1 = new Product_category_freg();

                return tab1;
            case 1:
                Product_category_freg tab2 = new Product_category_freg();

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

