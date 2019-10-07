package com.anriku.sclib.helpers;

import android.view.View;

import com.anriku.sclib.utils.ResUtils;

/**
 * Created by anriku on 2019-10-07.
 */
public class SCBackgroundDrawableHelper extends SCBackgroundHelper {

    public SCBackgroundDrawableHelper(View view) {
        super(view);
    }

    @Override
    public int[] recordAndReplaceResIds(int[] resIds) {
        if (resIds == null) {
            return null;
        }
        super.recordAndReplaceResIds(resIds);
        int[] newDrawableResIds = new int[resIds.length];
        newDrawableResIds[0] = ResUtils.getNewDrawableResId(resIds[0]);
        return newDrawableResIds;
    }

    @Override
    public void applySkinChange() {
        if (mResIds != null) {
            mView.setBackgroundResource(mResIds[0]);
        }
    }
}
