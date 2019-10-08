package com.anriku.sclib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

/**
 * Created by anriku on 2019-10-06.
 */
public class ResUtils {
    public static final String SP_NAME = "SkinChange";
    public static final String KEY = "skin_name";
    public static String sSkinSuffix = "_night";

    /**
     * 该方法应该在Application中进行初始化调用
     */
    public static void init(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SkinChange", 0);
        sSkinSuffix = sharedPreferences.getString("skin_name", "");
    }

    /**
     * 切换皮肤时进行调用
     */
    public static void changeSkin(Context context, String skinName) {
        Editor editor = context.getSharedPreferences("SkinChange", 0).edit();
        editor.putString("skin_name", skinName);
        editor.apply();
    }

    public static int getNewDrawableOrColorResId(Context context, int resId) {
        int newResId = resId;
        int type = DrawableTypeUtils.getIdType(context, resId);
        if (type == DrawableTypeUtils.TYPE_COLOR) {
            newResId = ResUtils.getNewColorResId(resId);
        } else if (type == DrawableTypeUtils.TYPE_DRAWABLE) {
            newResId = ResUtils.getNewDrawableResId(resId);
        }
        return newResId;
    }

    public static int getNewDrawableResId(int resId) {
        return resId;
    }

    public static int getNewColorResId(int resId) {
        return resId;
    }

    public static int getNewStringResId(int resId) {
        return resId;
    }

    public static int getNewStyleResId(int resId) {
        return resId;
    }

    /**
     * 根据不同的皮肤将原始的资源id替换为新的资源id。
     *
     * @param resId           原始资源id
     * @param integerToString 对应资源类型的Integer -> String的HashMap
     * @param stringToInteger 对应资源类型的String -> Integer的HashMap
     * @param skinSuffix      对应皮肤的后缀
     * @return 对应皮肤的资源id
     */
    private static int getNewResId(int resId, Map<Integer, String> integerToString,
                                   Map<String, Integer> stringToInteger, String skinSuffix) {
        if (integerToString == null || stringToInteger == null) {
            return resId;
        }
        String name = integerToString.get(resId);
        Integer newId = stringToInteger.get(name + skinSuffix);
        return newId == null ? resId : newId;
    }
}
