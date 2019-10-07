package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.anriku.sclib.annotation.SkinChangeFunction;
import com.anriku.sclib.annotation.SkinChangeFunctionArray;
import com.anriku.sclib.helpers.SCBackgroundColorHelper;
import com.anriku.sclib.helpers.SCBackgroundDrawableHelper;
import com.anriku.sclib.helpers.SCImageHelper;

/**
 * Created by anriku on 2019-10-07.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeFunctionArray(helperClassesWholeName = {SCBackgroundDrawableHelper.class, SCBackgroundColorHelper.class,
        SCImageHelper.class})
public class SCImageView extends ImageView {

    public SCImageView(Context context) {
        super(context);
    }

    public SCImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SCImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    @Override
    @SkinChangeFunction(helperClassWholeName = SCImageHelper.class,
            parameterIndexes = {0})
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }
}
