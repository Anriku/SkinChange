package com.anriku.sclib.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.anriku.sclib.widget.ViewInflater;

/**
 * Created by anriku on 2019-10-12.
 */

public class CustomFactory implements LayoutInflater.Factory2 {
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return ViewInflater.createView(context, name, attrs);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }
}
