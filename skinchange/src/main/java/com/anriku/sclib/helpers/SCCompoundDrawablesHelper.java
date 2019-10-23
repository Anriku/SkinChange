package com.anriku.sclib.helpers;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.anriku.sclib.R;
import com.anriku.sclib.utils.ResUtils;

/**
 * Created by anriku on 2019-10-07.
 */

public class SCCompoundDrawablesHelper extends SCHelper {

    public SCCompoundDrawablesHelper(View mView) {
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
            mResIds[0] = a.getResourceId(R.styleable.SCCompoundDrawablesHelper_android_drawableLeft, INVALID_ID);
            mResIds[1] = a.getResourceId(R.styleable.SCCompoundDrawablesHelper_android_drawableTop, INVALID_ID);
            mResIds[2] = a.getResourceId(R.styleable.SCCompoundDrawablesHelper_android_drawableRight, INVALID_ID);
            mResIds[3] = a.getResourceId(R.styleable.SCCompoundDrawablesHelper_android_drawableBottom, INVALID_ID);
        } finally {
            a.recycle();
        }
        applySkinChange();
    }

    @Override
    public int[] recordAndReplaceResIds(int[] resIds) {
        if (resIds == null || resIds.length != 4 || mView == null) {
            return resIds;
        }
        super.recordAndReplaceResIds(resIds);

        int[] newBackgroundResIds = new int[resIds.length];
        for (int i = 0; i < resIds.length; i++) {
            newBackgroundResIds[i] = ResUtils.getNewDrawableOrColorResId(mView.getContext(), resIds[i]);
        }

        return newBackgroundResIds;
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

            ((TextView) mView).setCompoundDrawablesWithIntrinsicBounds(
                    resIds[0],
                    resIds[1],
                    resIds[2],
                    resIds[3]
            );
        }
    }
}
