package com.anriku.sclib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by anriku on 2019-10-08.
 */

public class DrawableTypeUtils {

    public static final int TYPE_OTHER = -1;
    public static final int TYPE_DRAWABLE = 0;
    public static final int TYPE_COLOR = 1;


    /**
     * 获取Id是drawable、color或者其它类型
     */
    public static int getIdType(Context context, int id) {
        Drawable drawable;
        try {
            drawable = SCContextCompat.getDrawable(context, id);
        } catch (Resources.NotFoundException e) {
            return TYPE_OTHER;
        }
        if (drawable instanceof ColorDrawable) {
            return TYPE_COLOR;
        } else {
            return TYPE_DRAWABLE;
        }
    }

}
