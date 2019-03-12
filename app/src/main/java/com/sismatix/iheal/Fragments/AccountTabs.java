package com.sismatix.iheal.Fragments;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Adapter.AccountTabAdapter;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.View.CountDrawable;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountTabs extends Fragment {

  public static Toolbar toolbar_myaccount;

    /*TextView tv_tab_titles;*/
    TextView tv_myaccount_title;

    public AccountTabs() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account_tabs, container, false);

        tv_myaccount_title = (TextView) v.findViewById(R.id.tv_myaccount_title);
        //tv_tab_titles.setText("MY ACCOUNT");

        //option manu
        setHasOptionsMenu(true);
        toolbar_myaccount = (Toolbar) v.findViewById(R.id.toolbar_myaccount);
        // toolbar_mywishlist.setTitle("My Wishlist");

        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_myaccount);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);
/*
        setHasOptionsMenu(true);
        toolbar_myaccount = (Toolbar) v.findViewById(R.id.toolbar_myaccount);
        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar_myaccount);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);
*/

        final TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("MY ACCOUNT"));
        tabLayout.addTab(tabLayout.newTab().setText("MY ORDERS"));
        tabLayout.addTab(tabLayout.newTab().setText("MESSAGES"));
        tabLayout.addTab(tabLayout.newTab().setText("WISHLIST"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager_tabs);
        final AccountTabAdapter adapter = new AccountTabAdapter(getChildFragmentManager(), tabLayout.getTabCount());//getFragmentManager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    //tv_tab_titles.setText("MY ACCOUNT");
                    tv_myaccount_title.setText("MY ACCOUNT");
                } else if (tab.getPosition() == 1) {
                    //tv_tab_titles.setText("MY ORDERS");
                    tv_myaccount_title.setText("MY ORDERS");
                } else if (tab.getPosition() == 2) {
                    Meassge_fragment.toolbar_messg.setVisibility(View.GONE);
                    //tv_tab_titles.setText("MESSAGES");
                    tv_myaccount_title.setText("MESSAGES");
                } else if (tab.getPosition() == 3) {
                    Wishlist_fragment.toolbar_mywishlist.setVisibility(View.GONE);
                    //tv_tab_titles.setText("WISHLIST");
                    tv_myaccount_title.setText("WISHLIST");
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
       // inflater.inflate(R.menu.cart, menu);
        //MenuItem item = menu.findItem(R.id.cart);
     /*   MenuItem item_search = menu.findItem(R.id.search);
        item_search.setVisible(false);*/
       // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
