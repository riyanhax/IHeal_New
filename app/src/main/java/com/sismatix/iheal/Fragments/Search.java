package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sismatix.iheal.Activity.Search_activity;
import com.sismatix.iheal.R;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {

    LinearLayout lv_search;
    TextView tv_search,text;
    View v;

    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_search, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        lv_search=(LinearLayout)v.findViewById(R.id.lv_search);
        tv_search=(TextView)v.findViewById(R.id.tv_search);
        text=(TextView)v.findViewById(R.id.text);

        lv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
                /*Intent i=new Intent(getActivity(), Search_activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);*/
               /* getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);*/
            }
        });
        return v;
    }

}
