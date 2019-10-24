package com.anriku.sclib.helpers;

import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.anriku.sclib.R;
import com.anriku.sclib.utils.ResUtils;

/**
 * Created by anriku on 2019-10-08.
 */

public class SCTextAppearanceHelper extends SCHelper {

    public SCTextAppearanceHelper(View mView) {
        super(mView);
    }

    @Override
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (mResIds == null) {
            mResIds = new int[1];
        }
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.SCTextAppearanceHelper, defStyleAttr, defStyleRes);
        try {
            if (a.hasValue(R.styleable.SCTextAppearanceHelper_android_textAppearance)) {
                mResIds[0] = a.getResourceId(
                        R.styleable.SCTextAppearanceHelper_android_textAppearance, INVALID_ID);
            }
        } finally {
            a.recycle();
        }
        applySkinChange();
    }

    @Override
    public int[] recordAndReplaceResIds(int[] resIds) {
        if (resIds == null || mView == null) {
            return resIds;
        }
        super.recordAndReplaceResIds(resIds);

        int[] newBackgroundResIds = new int[resIds.length];
        newBackgroundResIds[0] = ResUtils.getNewStyleResId(resIds[0]);
        return newBackgroundResIds;
    }

    @Override
    public void applySkinChange() {
        if (mResIds != null && mResIds.length == 1 && mResIds[0] != INVALID_ID && mView instanceof TextView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ((TextView) mView).setTextAppearance(mResIds[0]);
            } else {
                ((TextView) mView).setTextAppearance(mView.getContext(), mResIds[0]);
            }
        }
    }
}
