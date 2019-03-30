package com.sismatix.iheal.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sismatix.iheal.R;

public class Confirmation_screen extends AppCompatActivity {
    LinearLayout lv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_confirmation_screen);
        lv_close=(LinearLayout)findViewById(R.id.lv_close);
        lv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Confirmation_screen.this,Navigation_drawer_activity.class);
                startActivity(intent);
            }
        });
    }
}
