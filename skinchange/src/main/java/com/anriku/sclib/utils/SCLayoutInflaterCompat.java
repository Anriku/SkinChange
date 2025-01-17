package com.anriku.sclib.utils;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterFactory;

import java.lang.reflect.Field;

/**
 * Created by anriku on 2019-10-12.
 */

public final class SCLayoutInflaterCompat {
    private static final String TAG = "LayoutInflaterCompatHC";

    private static Field sLayoutInflaterFactory2Field;
    private static boolean sCheckedField;

    /**
     * For APIs < 21, there was a framework bug that prevented a LayoutInflater's
     * Factory2 from being merged properly if set after a cloneInContext from a LayoutInflater
     * that already had a Factory2 registered. We work around that bug here. If we can't we
     * log an error.
     */
    private static void forceSetFactory2(LayoutInflater inflater, LayoutInflater.Factory2 factory) {
        if (!sCheckedField) {
            try {
                sLayoutInflaterFactory2Field = LayoutInflater.class.getDeclaredField("mFactory2");
                sLayoutInflaterFactory2Field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "forceSetFactory2 Could not find field 'mFactory2' on class "
                        + LayoutInflater.class.getName()
                        + "; inflation may have unexpected results.", e);
            }
            sCheckedField = true;
        }
        if (sLayoutInflaterFactory2Field != null) {
            try {
                sLayoutInflaterFactory2Field.set(inflater, factory);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "forceSetFactory2 could not set the Factory2 on LayoutInflater "
                        + inflater + "; inflation may have unexpected results.", e);
            }
        }
    }

    /*
     * Hide the constructor.
     */
    private SCLayoutInflaterCompat() {
    }

    /**
     * Attach a custom {@link LayoutInflater.Factory2} for creating views while using
     * this {@link LayoutInflater}. This must not be null, and can only be set once;
     * after setting, you can not change the factory.
     *
     * @see LayoutInflater#setFactory2(android.view.LayoutInflater.Factory2)
     */
    public static void setFactory2(
            @NonNull LayoutInflater inflater, @NonNull LayoutInflater.Factory2 factory) {
        inflater.setFactory2(factory);

        if (Build.VERSION.SDK_INT < 21) {
            final LayoutInflater.Factory f = inflater.getFactory();
            if (f instanceof LayoutInflater.Factory2) {
                // The merged factory is now set to getFactory(), but not getFactory2() (pre-v21).
                // We will now try and force set the merged factory to mFactory2
                forceSetFactory2(inflater, (LayoutInflater.Factory2) f);
            } else {
                // Else, we will force set the original wrapped Factory2
                forceSetFactory2(inflater, factory);
            }
        }
    }
}
