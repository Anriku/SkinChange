package com.anriku.sclib.helpers;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;

import com.anriku.sclib.R;
import com.anriku.sclib.utils.ResUtils;

/**
 * Created by anriku on 2019-10-08.
 */

public class SCButtonHelper extends SCHelper {

    public SCButtonHelper(View mView) {
        super(mView);
    }

    @Override
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        if (mResIds == null) {
            mResIds = new int[1];
        }
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs,
                R.styleable.SCButtonHelper, defStyleAttr, 0);
        try {
            mResIds[0] = a.getResourceId(R.styleable.SCButtonHelper_android_button, INVALID_ID);
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
        newBackgroundResIds[0] = ResUtils.getNewDrawableResId(resIds[0]);
        return newBackgroundResIds;
    }

    @Override
    public void applySkinChange() {
        if (mResIds != null && mResIds.length == 1 && mResIds[0] != INVALID_ID && mView instanceof CheckBox) {
            ((CheckBox) mView).setButtonDrawable(mResIds[0]);
        }
    }
}
