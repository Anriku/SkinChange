package com.anriku.sclib.helpers;

import android.view.View;

import com.anriku.sclib.utils.ResUtils;

/**
 * Created by anriku on 2019-10-07.
 */
public class SCBackgroundColorHelper extends SCBackgroundHelper {

    public SCBackgroundColorHelper(View view) {
        super(view);
    }

    @Override
    public int[] recordAndReplaceResIds(int[] resIds) {
        if (resIds == null) {
            return null;
        }
        super.recordAndReplaceResIds(resIds);
        int[] newColorResIds = new int[resIds.length];
        newColorResIds[0] = ResUtils.getNewColorResId(resIds[0]);
        return newColorResIds;
    }

    @Override
    public void applySkinChange() {
        if (mResIds != null) {
            mView.setBackgroundColor(mResIds[0]);
        }
    }
}
