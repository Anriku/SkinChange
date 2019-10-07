package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.anriku.sclib.annotation.SkinChangeFunction;
import com.anriku.sclib.helpers.SCBackgroundColorHelper;
import com.anriku.sclib.helpers.SCBackgroundDrawableHelper;

/**
 * Created by anriku on 2019-10-07.
 */

@SuppressLint("AppCompatCustomView")
public class SCTextView extends TextView {

    public SCTextView(Context context) {
        super(context);
    }

    public SCTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SCTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCBackgroundColorHelper.class,
            parameterIndexes = {0})
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCBackgroundDrawableHelper.class,
            parameterIndexes = {0})
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
    }
}
