package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCButtonHelper;
import com.anriku.sclib.helpers.SCCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCRelativeCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCTextAppearanceHelper;

/**
 * Created by anriku on 2019-10-08.
 */

public class SCAppCompactCheckBox extends AppCompatCheckBox implements SkinChange {

    private final SCBackgroundHelper mSCBackgroundDrawableHelper;
    private final SCTextAppearanceHelper mSCTextAppearanceHelper;
    private final SCCompoundDrawablesHelper mSCCompoundDrawablesHelper;
    private final SCRelativeCompoundDrawablesHelper mSCRelativeCompoundDrawablesHelper;
    private final SCButtonHelper mSCButtonHelper;

    public SCAppCompactCheckBox(Context context) {
        this(context, null);
    }

    public SCAppCompactCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkboxStyle);
    }

    public SCAppCompactCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSCBackgroundDrawableHelper = new SCBackgroundHelper(this);
        mSCTextAppearanceHelper = new SCTextAppearanceHelper(this);
        mSCCompoundDrawablesHelper = new SCCompoundDrawablesHelper(this);
        mSCRelativeCompoundDrawablesHelper = new SCRelativeCompoundDrawablesHelper(this);
        mSCButtonHelper = new SCButtonHelper(this);

        mSCBackgroundDrawableHelper.loadFromAttributes(attrs, defStyleAttr);
        mSCTextAppearanceHelper.loadFromAttributes(attrs, defStyleAttr);
        mSCCompoundDrawablesHelper.loadFromAttributes(attrs, defStyleAttr);
        mSCRelativeCompoundDrawablesHelper.loadFromAttributes(attrs, defStyleAttr);
        mSCButtonHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setTextAppearance(int resId) {
        int[] newResIds = mSCTextAppearanceHelper.recordAndReplaceResIds(new int[]{resId});
        if (newResIds != null) {
            resId = newResIds[0];
        }
        super.setTextAppearance(resId);
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        int[] newResIds = mSCTextAppearanceHelper.recordAndReplaceResIds(new int[]{resId});
        if (newResIds != null) {
            resId = newResIds[0];
        }
        super.setTextAppearance(context, resId);
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        int[] newResIds = mSCRelativeCompoundDrawablesHelper.recordAndReplaceResIds(new int[]{
                start, top, end, bottom
        });
        if (newResIds != null) {
            start = newResIds[0];
            top = newResIds[1];
            end = newResIds[2];
            bottom = newResIds[3];
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        int[] newResIds = mSCCompoundDrawablesHelper.recordAndReplaceResIds(new int[]{
                left, top, right, bottom
        });
        if (newResIds != null) {
            left = newResIds[0];
            top = newResIds[1];
            right = newResIds[2];
            bottom = newResIds[3];
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override
    public void setBackgroundResource(int resId) {
        int[] newResIds = mSCBackgroundDrawableHelper.recordAndReplaceResIds(new int[]{resId});
        if (newResIds != null) {
            resId = newResIds[0];
        }
        super.setBackgroundResource(resId);
    }

    @Override
    public void setButtonDrawable(int resId) {
        int[] newResIds = mSCButtonHelper.recordAndReplaceResIds(new int[]{resId});
        if (newResIds != null) {
            resId = newResIds[0];
        }
        super.setButtonDrawable(resId);
    }

    @Override
    public void applySkinChange() {
        mSCBackgroundDrawableHelper.applySkinChange();
        mSCTextAppearanceHelper.applySkinChange();
        mSCCompoundDrawablesHelper.applySkinChange();
        mSCRelativeCompoundDrawablesHelper.applySkinChange();
        mSCButtonHelper.applySkinChange();
    }
}
