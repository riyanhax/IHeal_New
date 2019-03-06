package com.sismatix.iheal.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sismatix.iheal.Fragments.AccountTabs;
import com.sismatix.iheal.Fragments.Meassge_fragment;
import com.sismatix.iheal.Fragments.MyOrder;
import com.sismatix.iheal.Fragments.My_Account_With_Login;
import com.sismatix.iheal.Fragments.Wishlist_fragment;

public class AccountTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public AccountTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                My_Account_With_Login tab1 = new My_Account_With_Login();
                return tab1;
            case 1:
                MyOrder tab2 = new MyOrder();
                return tab2;

            case 2:
                Meassge_fragment tab3 = new Meassge_fragment();
                return tab3;

            case 3:
                Wishlist_fragment tab4 = new Wishlist_fragment();
                return tab4;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}