package com.anriku.scplugin.utils;

/**
 * Created by anriku on 2019-10-10.
 */

public class PackageNameUtils {

    private static final String ANDROID_X_VIEW_PACKAGE_NAME = "androidx/appcompat/widget";
    private static final String SUPPORT_VIEW_PACKAGE_NAME = "android/support/v7/widget";
    public static String sViewPackageName = ANDROID_X_VIEW_PACKAGE_NAME;

    /**
     * 设置兼容库类型
     *
     * @param isAndroidX 是否是AndroidX
     */
    public static void setCompatibleType(boolean isAndroidX) {
        if (isAndroidX) {
            sViewPackageName = ANDROID_X_VIEW_PACKAGE_NAME;
        } else {
            sViewPackageName = SUPPORT_VIEW_PACKAGE_NAME;
        }
    }
}
