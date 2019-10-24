package com.anriku.sclib.helpers;

import android.util.AttributeSet;
import android.view.View;

/**
 * 所有资源Helper类的父类
 * <p>
 * Created by anriku on 2019-10-06.
 */
public abstract class SCHelper {

    public static final int INVALID_ID = 0;
    protected int[] mResIds;
    protected View mView;

    public SCHelper(View mView) {
        this.mView = mView;
    }

    public abstract void loadFromAttributes(AttributeSet attrs, int defStyleAttr, int defStyleRes);

    /**
     * 用于记录并且替换成新的resIds。
     * PS:替换成新的resIds的逻辑在子类中实现。
     *
     * @param resIds 资源id数组
     * @return 根据皮肤替换后的资源id数组
     */
    public int[] recordAndReplaceResIds(int[] resIds) {
        mResIds = resIds;
        return resIds;
    }

    public abstract void applySkinChange();

}
