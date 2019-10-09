package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCRelativeCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCTextAppearanceHelper;

/**
 * Created by anriku on 2019-10-08.
 */

public class SCAppCompatEditText extends AppCompatEditText implements SkinChange {

    private final SCBackgroundHelper mSCBackgroundHelper;
    private final SCTextAppearanceHelper mSCTextAppearanceHelper;
    private final SCCompoundDrawablesHelper mSCCompoundDrawablesHelper;
    private final SCRelativeCompoundDrawablesHelper mSCRelativeCompoundDrawablesHelper;

    public SCAppCompatEditText(Context context) {
        this(context, null);
    }

    public SCAppCompatEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public SCAppCompatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSCBackgroundHelper = new SCBackgroundHelper(this);
        mSCTextAppearanceHelper = new SCTextAppearanceHelper(this);
        mSCCompoundDrawablesHelper = new SCCompoundDrawablesHelper(this);
        mSCRelativeCompoundDrawablesHelper = new SCRelativeCompoundDrawablesHelper(this);

        mSCBackgroundHelper.loadFromAttributes(attrs, defStyleAttr);
        mSCTextAppearanceHelper.loadFromAttributes(attrs, defStyleAttr);
        mSCCompoundDrawablesHelper.loadFromAttributes(attrs, defStyleAttr);
        mSCRelativeCompoundDrawablesHelper.loadFromAttributes(attrs, defStyleAttr);
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
        int[] newResIds = mSCBackgroundHelper.recordAndReplaceResIds(new int[]{resId});
        if (newResIds != null) {
            resId = newResIds[0];
        }
        super.setBackgroundResource(resId);
    }

    @Override
    public void applySkinChange() {
        mSCBackgroundHelper.applySkinChange();
        mSCTextAppearanceHelper.applySkinChange();
        mSCCompoundDrawablesHelper.applySkinChange();
        mSCRelativeCompoundDrawablesHelper.applySkinChange();
    }
}
