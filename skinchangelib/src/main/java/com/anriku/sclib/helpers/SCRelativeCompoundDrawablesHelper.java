package com.anriku.sclib.helpers;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.anriku.sclib.R;

/**
 * Created by anriku on 2019-10-08.
 */

public class SCRelativeCompoundDrawablesHelper extends SCCompoundDrawablesHelper {

    public SCRelativeCompoundDrawablesHelper(View mView) {
        super(mView);
    }

    @Override
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        if (mResIds == null) {
            mResIds = new int[4];
        }
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs,
                R.styleable.SCCompoundDrawablesHelper, defStyleAttr, 0);
        try {
            mResIds[0] = a.getResourceId(R.styleable.SCCompoundDrawablesHelper_android_drawableStart, INVALID_ID);
            mResIds[1] = a.getResourceId(R.styleable.SCCompoundDrawablesHelper_android_drawableTop, INVALID_ID);
            mResIds[2] = a.getResourceId(R.styleable.SCCompoundDrawablesHelper_android_drawableEnd, INVALID_ID);
            mResIds[3] = a.getResourceId(R.styleable.SCCompoundDrawablesHelper_android_drawableBottom, INVALID_ID);
        } finally {
            a.recycle();
        }
        applySkinChange();
    }

    @Override
    public void applySkinChange() {
        if (mResIds != null && mResIds.length == 4 && mView instanceof TextView) {
            int[] resIds = new int[mResIds.length];
            for (int i = 0; i < mResIds.length; i++) {
                if (mResIds[i] == INVALID_ID) {
                    resIds[i] = 0;
                } else {
                    resIds[i] = mResIds[i];
                }
            }

            if (resIds[0] != INVALID_ID || resIds[2] != INVALID_ID) {
                ((TextView) mView).setCompoundDrawablesRelativeWithIntrinsicBounds(
                        resIds[0],
                        resIds[1],
                        resIds[2],
                        resIds[3]
                );
            }
        }
    }
}
