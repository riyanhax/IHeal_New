package com.sismatix.iheal.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Meassge_fragment extends Fragment {


    public Meassge_fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_meassge_fragment, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        return v;
    }

}
