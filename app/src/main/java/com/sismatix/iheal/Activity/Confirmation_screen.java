package com.sismatix.iheal.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sismatix.iheal.R;

public class Confirmation_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_confirmation_screen);
    }
}
