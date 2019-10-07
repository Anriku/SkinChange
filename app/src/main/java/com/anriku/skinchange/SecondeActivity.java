package com.anriku.skinchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SecondeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);
        System.out.println(R.id.tv);
    }
}
