package com.sismatix.iheal.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.sismatix.iheal.R;
import com.sismatix.iheal.sdk.JivoDelegate;
import com.sismatix.iheal.sdk.JivoSdk;

import java.util.Locale;

public class Chat_meassge extends AppCompatActivity implements JivoDelegate {
    ImageView iv_close_chat;
    //**************
    JivoSdk jivoSdk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat_meassge);

        iv_close_chat=(ImageView)findViewById(R.id.iv_close_chat);

        iv_close_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Chat_meassge.this,Navigation_drawer_activity.class);
                startActivity(intent);
            }
        });

        String lang = Locale.getDefault().getLanguage().indexOf("ru") >= 0 ? "ru": "en";

        //*********************************************************
        jivoSdk = new JivoSdk((WebView) findViewById(R.id.webview), lang);
        jivoSdk.delegate = this;
        jivoSdk.prepare();
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Chat_meassge.this,Navigation_drawer_activity.class);
        startActivity(intent);
        finish();
    }
}
