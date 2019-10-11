package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.anriku.sclib.annotation.SkinChangeFunction;
import com.anriku.sclib.annotation.SkinChangeFunctionArray;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCCompoundDrawablesHelper;

/**
 * Created by anriku on 2019-10-07.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeFunctionArray(helperClassesWholeName = {SCBackgroundHelper.class, SCCompoundDrawablesHelper.class})
public class SCTextView extends TextView {

    public SCTextView(Context context) {
        this(context, null);
    }

    public SCTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public SCTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCBackgroundHelper.class,
            parameterIndexes = {0})
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCCompoundDrawablesHelper.class,
            parameterIndexes = {0, 1, 2, 3})
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
}
