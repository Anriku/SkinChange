package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.anriku.sclib.annotation.SkinChangeFunction;
import com.anriku.sclib.annotation.SkinChangeFunctionArray;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCImageHelper;

/**
 * Created by anriku on 2019-10-07.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeFunctionArray(helperClassesWholeName = {SCBackgroundHelper.class,
        SCImageHelper.class})
public class SCImageView extends ImageView {

    public SCImageView(Context context) {
        this(context, null);
    }

    public SCImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCBackgroundHelper.class,
            parameterIndexes = {0})
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCImageHelper.class,
            parameterIndexes = {0})
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }
}
