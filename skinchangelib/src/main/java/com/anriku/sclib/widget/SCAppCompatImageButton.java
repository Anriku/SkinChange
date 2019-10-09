package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageButton;

import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCImageHelper;

/**
 * Created by anriku on 2019-10-08.
 */

public class SCAppCompatImageButton extends AppCompatImageButton implements SkinChange {

    private final SCImageHelper mSCImageHelper;
    private final SCBackgroundHelper mSCBackgroundHelper;

    public SCAppCompatImageButton(Context context) {
        this(context, null);
    }

    public SCAppCompatImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.imageButtonStyle);
    }

    public SCAppCompatImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSCImageHelper = new SCImageHelper(this);
        mSCBackgroundHelper = new SCBackgroundHelper(this);

        mSCImageHelper.loadFromAttributes(attrs, defStyleAttr);
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
    public void setImageResource(int resId) {
        int[] newResIds = mSCImageHelper.recordAndReplaceResIds(new int[]{resId});
        if (newResIds != null) {
            resId = newResIds[0];
        }
        super.setImageResource(resId);
    }

    @Override
    public void applySkinChange() {
        mSCImageHelper.applySkinChange();
        mSCBackgroundHelper.applySkinChange();
    }
}
