package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.anriku.sclib.helpers.SCBackgroundHelper;

/**
 * Created by anriku on 2019-10-08.
 */

public class SCAppCompatSeekBar extends AppCompatSeekBar implements SkinChange {

    private final SCBackgroundHelper mSCBackgroundHelper;

    public SCAppCompatSeekBar(Context context) {
        this(context, null);
    }

    public SCAppCompatSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public SCAppCompatSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

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
