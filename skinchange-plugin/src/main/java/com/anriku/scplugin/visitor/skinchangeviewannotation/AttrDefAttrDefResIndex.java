package com.anriku.scplugin.visitor.skinchangeviewannotation;

/**
 * Created by anriku on 2019-10-24.
 */

public class AttrDefAttrDefResIndex {

    public static final int INVALID_INDEX = -1;
    private int mAttrsIndex;
    private int mDefStyleAttrIndex;
    private int mDefStyleResIndex;

    public AttrDefAttrDefResIndex(int attrsIndex, int defStyleAttrIndex, int defStyleResIndex) {
        mAttrsIndex = attrsIndex;
        mDefStyleAttrIndex = defStyleAttrIndex;
        mDefStyleResIndex = defStyleResIndex;
    }

    public int getAttrsIndex() {
        return mAttrsIndex;
    }

    public int getDefStyleAttrIndex() {
        return mDefStyleAttrIndex;
    }

    public int getDefStyleResIndex() {
        return mDefStyleResIndex;
    }
}
