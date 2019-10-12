package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.anriku.sclib.annotation.SkinChangeFunction;
import com.anriku.sclib.annotation.SkinChangeFunctionArray;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCImageHelper;

/**
 * Created by anriku on 2019-10-12.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeFunctionArray(helperClassesWholeName = {
        SCBackgroundHelper.class,
        SCImageHelper.class
})
public class SCImageButton extends ImageButton implements SkinChange {

    public SCImageButton(Context context) {
        this(context, null);
    }

    public SCImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.imageButtonStyle);
    }

    public SCImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCBackgroundHelper.class,
            parameterIndexes = {0})
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCImageHelper.class,
            parameterIndexes = {0})
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    @Override
    public void applySkinChange() {

    }
}
