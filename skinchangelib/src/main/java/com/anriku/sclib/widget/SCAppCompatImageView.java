package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.anriku.sclib.helpers.SCBackgroundColorHelper;
import com.anriku.sclib.helpers.SCBackgroundDrawableHelper;
import com.anriku.sclib.helpers.SCImageHelper;

/**
 * Created by anriku on 2019-10-07.
 */

public class SCAppCompatImageView extends AppCompatImageView {

    private final SCImageHelper mSCImageHelper;
    private final SCBackgroundColorHelper mSCBackgroundColorHelper;
    private final SCBackgroundDrawableHelper mSCBackgroundDrawableHelper;

    public SCAppCompatImageView(Context context) {
        this(context, null);
    }

    public SCAppCompatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCAppCompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSCImageHelper = new SCImageHelper(this);
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

    @Override
    public void setImageResource(int resId) {
        int[] newImageResIds = mSCImageHelper.recordAndReplaceResIds(new int[]{resId});
        resId = newImageResIds[0];
        super.setImageResource(resId);
    }
}
