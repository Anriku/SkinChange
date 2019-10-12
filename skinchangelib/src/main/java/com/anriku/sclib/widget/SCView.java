package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.anriku.sclib.annotation.SkinChangeFunction;
import com.anriku.sclib.annotation.SkinChangeFunctionArray;
import com.anriku.sclib.helpers.SCBackgroundHelper;


/**
 * Created by anriku on 2019-10-05.
 */
@SkinChangeFunctionArray(helperClassesWholeName = {
        SCBackgroundHelper.class
})
public class SCView extends View implements SkinChange {

    public SCView(Context context) {
        super(context);
    }

    public SCView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    @SkinChangeFunction(helperClassWholeName = SCBackgroundHelper.class,
            parameterIndexes = {0})
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
    }

    @Override
    public void applySkinChange() {

    }
}
