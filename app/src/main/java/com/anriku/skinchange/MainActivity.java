package com.anriku.skinchange;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anriku.sclib.utils.ResUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.actv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResUtils.changeSkin(MainActivity.this, "", getWindow().getDecorView());
            }
        });
        TextView textView = new TextView(this);
        textView.setBackgroundResource(R.color.test_color);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_audiotrack_black_24dp, 0, 0, 0);
        ((LinearLayout)findViewById(R.id.ll)).addView(textView);
    }

}
