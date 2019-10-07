package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.anriku.sclib.annotation.SkinChangeFunction;
import com.anriku.sclib.annotation.SkinChangeFunctionArray;
import com.anriku.sclib.helpers.SCBackgroundColorHelper;
import com.anriku.sclib.helpers.SCBackgroundDrawableHelper;


/**
 * Created by anriku on 2019-10-05.
 */
@SkinChangeFunctionArray(helperClassesWholeName = {SCBackgroundColorHelper.class,
        SCBackgroundDrawableHelper.class})
public class SCView extends View {

    public SCView(Context context) {
        super(context);
    }

    public SCView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SCView(Context context, AttributeSet attrs, int defStyleAttr) {
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
