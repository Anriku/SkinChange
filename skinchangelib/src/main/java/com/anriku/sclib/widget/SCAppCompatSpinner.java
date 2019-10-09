package com.anriku.sclib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSpinner;

import com.anriku.sclib.helpers.SCBackgroundHelper;

/**
 * Created by anriku on 2019-10-09.
 */

public class SCAppCompatSpinner extends AppCompatSpinner implements SkinChange {

    private final SCBackgroundHelper mSCBackgroundHelper;

    public SCAppCompatSpinner(Context context) {
        this(context, null);
    }

    public SCAppCompatSpinner(Context context, int mode) {
        this(context, null, android.R.attr.spinnerStyle, mode);
    }

    public SCAppCompatSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.spinnerStyle);
    }

    public SCAppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public SCAppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        this(context, attrs, defStyleAttr, mode, null);
    }

    public SCAppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);

        mSCBackgroundHelper = new SCBackgroundHelper(this);

        mSCBackgroundHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(int resId) {
        int[] newResIds = mSCBackgroundHelper.recordAndReplaceResIds(new int[]{resId});
        if (newResIds != null) {
            resId = newResIds[0];
        }
        super.setBackgroundResource(resId);
    }

    @Override
    public void applySkinChange() {
        mSCBackgroundHelper.applySkinChange();
    }
}
