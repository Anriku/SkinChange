package com.anriku.sclib.helpers;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.anriku.sclib.R;

/**
 * Created by anriku on 2019-10-05.
 */
public abstract class SCBackgroundHelper extends SCHelper {

    public SCBackgroundHelper(View view) {
        super(view);
    }

    /**
     * 获取View的背景资源id
     */
    @Override
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        if (mResIds == null) {
            mResIds = new int[1];
        }
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.SCBackgroundHelper, defStyleAttr, 0);
        try {
            if (a.hasValue(R.styleable.SCBackgroundHelper_android_background)) {
                mResIds[0] = a.getResourceId(
                        R.styleable.SCBackgroundHelper_android_background, INVALID_ID);
            }
        } finally {
            a.recycle();
        }
        applySkinChange();
    }

}
