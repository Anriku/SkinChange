package com.anriku.scplugin.utils;

import java.io.File;
import java.util.HashMap;

/**
 * Created by anriku on 2019-10-09.
 */

public class ReplaceNewViewUtils {

    public static final String SC_APP_COMPAT_PACKAGE = "com/anriku/sclib/widget" + File.separator;
    public static final String ANDROID_WIDGET = "android.widget".replace(".", "/") + File.separator;
    public static final String COMPATIBLE_WIDGET = PackageNameUtils.sViewPackageName.replace(".", "/") + File.separator;

    public static HashMap<String, String> sNeedReplaceViews = new HashMap<>();

    static {
        sNeedReplaceViews.put(ANDROID_WIDGET + "TextView", SC_APP_COMPAT_PACKAGE + "SCTextView");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatTextView");
        sNeedReplaceViews.put(ANDROID_WIDGET + "ImageView", SC_APP_COMPAT_PACKAGE + "SCImageView");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatImageView", SC_APP_COMPAT_PACKAGE + "SCAppCompatImageView");
        sNeedReplaceViews.put(ANDROID_WIDGET + "Button", SC_APP_COMPAT_PACKAGE + "SCButton");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatButton");
        sNeedReplaceViews.put(ANDROID_WIDGET + "EditText", SC_APP_COMPAT_PACKAGE + "SCEditText");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatEditText", SC_APP_COMPAT_PACKAGE + "SCAppCompatEditText");
        sNeedReplaceViews.put(ANDROID_WIDGET + "Spinner", SC_APP_COMPAT_PACKAGE + "SCSpinner");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatSpinner", SC_APP_COMPAT_PACKAGE + "SCAppCompatSpinner");
        sNeedReplaceViews.put(ANDROID_WIDGET + "ImageButton", SC_APP_COMPAT_PACKAGE + "SCImageButton");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatImageButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatImageButton");
        sNeedReplaceViews.put(ANDROID_WIDGET + "CheckBox", SC_APP_COMPAT_PACKAGE + "SCCheckBox");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatCheckBox", SC_APP_COMPAT_PACKAGE + "SCAppCompatCheckBox");
        sNeedReplaceViews.put(ANDROID_WIDGET + "RadioButton", SC_APP_COMPAT_PACKAGE + "SCRadioButton");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatRadioButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatRadioButton");
        sNeedReplaceViews.put(ANDROID_WIDGET + "CheckedTextView", SC_APP_COMPAT_PACKAGE + "SCCheckedTextView");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatCheckedTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatCheckedTextView");
        sNeedReplaceViews.put(ANDROID_WIDGET + "AutoCompleteTextView", SC_APP_COMPAT_PACKAGE + "SCAutoCompleteTextView");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatAutoCompleteTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatAutoCompleteTextView");
        sNeedReplaceViews.put(ANDROID_WIDGET + "MultiAutoCompleteTextView", SC_APP_COMPAT_PACKAGE + "SCMultiAutoCompleteTextView");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatMultiAutoCompleteTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatMultiAutoCompleteTextView");
        sNeedReplaceViews.put(ANDROID_WIDGET + "RatingBar", SC_APP_COMPAT_PACKAGE + "SCRatingBar");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatRatingBar", SC_APP_COMPAT_PACKAGE + "SCAppCompatRatingBar");
        sNeedReplaceViews.put(ANDROID_WIDGET + "SeekBar", SC_APP_COMPAT_PACKAGE + "SCSeekBar");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatSeekBar", SC_APP_COMPAT_PACKAGE + "SCAppCompatSeekBar");
        sNeedReplaceViews.put(ANDROID_WIDGET + "ToggleButton", SC_APP_COMPAT_PACKAGE + "SCToggleButton");
        sNeedReplaceViews.put(COMPATIBLE_WIDGET + "AppCompatToggleButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatToggleButton");
    }

}
