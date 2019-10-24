package com.anriku.sclib.helpers;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.anriku.sclib.R;
import com.anriku.sclib.utils.DrawableTypeUtils;
import com.anriku.sclib.utils.ResUtils;

/**
 * Created by anriku on 2019-10-05.
 */
public class SCBackgroundHelper extends SCHelper {

    public SCBackgroundHelper(View view) {
        super(view);
    }

    @Override
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (mResIds == null) {
            mResIds = new int[1];
        }
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs,
                R.styleable.SCBackgroundHelper, defStyleAttr, defStyleRes);
        try {
            mResIds[0] = a.getResourceId(R.styleable.SCBackgroundHelper_android_background, INVALID_ID);
        } finally {
            a.recycle();
        }
        applySkinChange();
    }

    @Override
    public int[] recordAndReplaceResIds(int[] resIds) {
        if (resIds == null || resIds.length != 1 || mView == null) {
            return resIds;
        }
        super.recordAndReplaceResIds(resIds);

        int[] newBackgroundResIds = new int[resIds.length];
        newBackgroundResIds[0] = ResUtils.getNewDrawableOrColorResId(mView.getContext(), resIds[0]);
        return newBackgroundResIds;
    }

    @Override
    public void applySkinChange() {
        if (mResIds != null && mResIds.length == 1 && mResIds[0] != INVALID_ID) {
            mView.setBackgroundResource(mResIds[0]);
        }
    }

}
