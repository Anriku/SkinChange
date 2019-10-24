package com.anriku.scplugin.visitor.skinchangeviewannotation;

import org.objectweb.asm.Type;

/**
 * Created by anriku on 2019-10-24.
 */

public class HelperClassAndParameterIndexes {
    private Type mHelperClassType;
    private int[] mParameterIndexes;

    public HelperClassAndParameterIndexes(Type helperClassType, int[] parameterIndexes) {
        this.mHelperClassType = helperClassType;
        this.mParameterIndexes = parameterIndexes;
    }

    public Type getHelperClassType() {
        return mHelperClassType;
    }

    public int[] getParameterIndexes() {
        return mParameterIndexes;
    }
}