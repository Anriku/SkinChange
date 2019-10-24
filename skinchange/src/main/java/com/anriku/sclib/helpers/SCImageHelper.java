package com.anriku.sclib.helpers;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.anriku.sclib.R;
import com.anriku.sclib.utils.ResUtils;

/**
 * Created by anriku on 2019-10-06.
 */

public class SCImageHelper extends SCHelper {

    public SCImageHelper(ImageView view) {
        super(view);
    }

    @Override
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (mResIds == null) {
            mResIds = new int[1];
        }
        TypedArray a = null;
        try {
            a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.SCImageHelper, defStyleAttr, defStyleRes);
            mResIds[0] = a.getResourceId(R.styleable.SCImageHelper_android_src, INVALID_ID);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        applySkinChange();
    }

    @Override
    public int[] recordAndReplaceResIds(int[] resIds) {
        if (resIds == null || resIds.length != 1) {
            return null;
        }
        super.recordAndReplaceResIds(resIds);
        int[] newImageResIds = new int[resIds.length];
        newImageResIds[0] = ResUtils.getNewDrawableResId(resIds[0]);
        return newImageResIds;
    }

    @Override
    public void applySkinChange() {
        if (mResIds != null && mResIds.length == 1 && mResIds[0] != INVALID_ID && mView instanceof ImageView) {
            ((ImageView) mView).setImageResource(mResIds[0]);
        }
    }
}
