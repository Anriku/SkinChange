package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.anriku.sclib.annotation.SkinChangeFunction;
import com.anriku.sclib.annotation.SkinChangeFunctionArray;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCButtonHelper;
import com.anriku.sclib.helpers.SCCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCRelativeCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCTextAppearanceHelper;

/**
 * Created by anriku on 2019-10-12.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeFunctionArray(helperClassesWholeName = {
        SCTextAppearanceHelper.class,
        SCRelativeCompoundDrawablesHelper.class,
        SCCompoundDrawablesHelper.class,
        SCBackgroundHelper.class,
        SCButtonHelper.class
})
public class SCCheckBox extends CheckBox implements SkinChange {

    public SCCheckBox(Context context) {
        this(context, null);
    }

    public SCCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkboxStyle);
    }

    public SCCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCTextAppearanceHelper.class,
            parameterIndexes = {0})
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCTextAppearanceHelper.class,
            parameterIndexes = {1})
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCRelativeCompoundDrawablesHelper.class,
            parameterIndexes = {0, 1, 2, 3})
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCCompoundDrawablesHelper.class,
            parameterIndexes = {0, 1, 2, 3})
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCBackgroundHelper.class, parameterIndexes = {0})
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCButtonHelper.class,
            parameterIndexes = {0})
    public void setButtonDrawable(int resId) {
        super.setButtonDrawable(resId);
    }

    @Override
    public void applySkinChange() {

    }
}