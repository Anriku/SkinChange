package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.anriku.sclib.helpers.SCBackgroundColorHelper;
import com.anriku.sclib.helpers.SCBackgroundDrawableHelper;

/**
 * Created by anriku on 2019-10-07.
 */

public class SCAppCompatTextView extends AppCompatTextView {

    private final SCBackgroundColorHelper mSCBackgroundColorHelper;
    private final SCBackgroundDrawableHelper mSCBackgroundDrawableHelper;

    public SCAppCompatTextView(Context context) {
        this(context, null);
    }

    public SCAppCompatTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCAppCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSCBackgroundColorHelper = new SCBackgroundColorHelper(this);
        mSCBackgroundDrawableHelper = new SCBackgroundDrawableHelper(this);
    }

    @Override
    public void setBackgroundColor(int color) {
        int[] newColorResIds = mSCBackgroundColorHelper.recordAndReplaceResIds(new int[]{color});
        color = newColorResIds[0];
        super.setBackgroundColor(color);
    }

    @Override
    public void setBackgroundResource(int resId) {
        int[] newDrawableResIds = mSCBackgroundDrawableHelper.recordAndReplaceResIds(new int[]{resId});
        resId = newDrawableResIds[0];
        super.setBackgroundResource(resId);
    }
}
