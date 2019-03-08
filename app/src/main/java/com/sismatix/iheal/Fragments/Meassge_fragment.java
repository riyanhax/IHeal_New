package com.sismatix.iheal.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.sismatix.iheal.R;
import com.sismatix.iheal.sdk.JivoDelegate;
import com.sismatix.iheal.sdk.JivoSdk;

import java.util.Locale;

import static com.sismatix.iheal.Activity.Navigation_drawer_activity.bottom_navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Meassge_fragment extends Fragment implements JivoDelegate {

    JivoSdk jivoSdk;
    public Meassge_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_meassge_fragment, container, false);
        bottom_navigation.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        String lang = Locale.getDefault().getLanguage().indexOf("ru") >= 0 ? "ru": "en";
        //*********************************************************
        jivoSdk = new JivoSdk((WebView)v.findViewById(R.id.webview), lang);
        jivoSdk.delegate = this;
        jivoSdk.prepare();
        return v;
    }

    @Override
    public void onEvent(String name, String data) {
        if(name.equals("url.click")){
            if(data.length() > 2){
                String url = data.substring(1, data.length() - 1);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        }
    }
}
