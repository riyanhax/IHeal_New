package com.sismatix.iheal.Fragments;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sismatix.iheal.Activity.Navigation_drawer_activity;
import com.sismatix.iheal.Preference.Login_preference;
import com.sismatix.iheal.R;
import com.sismatix.iheal.View.CountDrawable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderDetails extends Fragment {

    Toolbar toolbar;
    String customer_id_final,order_id_final;
    public MyOrderDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_my_order_details, container, false);
        toolbar=(Toolbar)v.findViewById(R.id.toolbar_order_detail);
        setHasOptionsMenu(true);
        //set back icon by defult
        ((Navigation_drawer_activity) getActivity()).setSupportActionBar(toolbar);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((Navigation_drawer_activity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white_36dp);

        customer_id_final = Login_preference.getcustomer_id(getContext());
        Log.e("cust_final",""+customer_id_final);

        Bundle bundle = this.getArguments();

        if (bundle != null) {

            order_id_final = bundle.getString("orderrr_id");
            Log.e("order_id_final", "" + order_id_final);
            call_order_detail_api(order_id_final,customer_id_final);

        }

        return v;
    }

    private void call_order_detail_api(String order_id_final, String customer_id_final) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear();
        // inflater.inflate(R.menu.cart, menu);
        //MenuItem item = menu.findItem(R.id.cart);
        MenuItem item_search = menu.findItem(R.id.search);
        item_search.setVisible(false);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
