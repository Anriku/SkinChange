package com.anriku.sclib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.ViewGroup;

import com.anriku.sclib.widget.SkinChange;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by anriku on 2019-10-06.
 */
public class ResUtils {
    public static final String SP_NAME = "SkinChange";
    public static final String KEY = "skin_name";
    private static String sSkinSuffix = "_" + MD5Utils.md5Hex("night");

    /**
     * 该方法应该在Application中进行初始化调用
     */
    public static void init(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SkinChange", 0);
        String skinName = sharedPreferences.getString("skin_name", "");
        if (!skinName.isEmpty()) {
            sSkinSuffix = MD5Utils.md5Hex(skinName);
        } else {
            sSkinSuffix = "";
        }
    }

    /**
     * 切换皮肤时进行调用
     */
    public static void changeSkin(Context context, String skinName, View view) {
        if (skinName == null || skinName.isEmpty()) {
            sSkinSuffix = "";
            skinName = "";
        } else {
            sSkinSuffix = "_" + MD5Utils.md5Hex(skinName);
        }

        Editor editor = context.getSharedPreferences("SkinChange", 0).edit();
        editor.putString("skin_name", skinName);
        editor.apply();

        if (view != null) {
            applyChangeToView(view);
        }
    }

    private static void applyChangeToView(View view) {
        Queue<View> queue = new LinkedList<>();
        queue.add(view);
        while (!queue.isEmpty()) {
            View tempView = queue.poll();
            if (tempView instanceof SkinChange) {
                ((SkinChange) tempView).applySkinChange();
            }
            if (tempView instanceof ViewGroup) {
                ViewGroup tempViewGroup = (ViewGroup) tempView;
                int count = tempViewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    queue.offer(tempViewGroup.getChildAt(i));
                }
            }
        }
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
