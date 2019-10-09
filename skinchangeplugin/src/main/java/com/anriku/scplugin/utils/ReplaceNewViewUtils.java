package com.anriku.scplugin.utils;

import java.io.File;
import java.util.HashMap;

/**
 * Created by anriku on 2019-10-09.
 */

public class ReplaceNewViewUtils {

    public static final String SC_APP_COMPAT_PACKAGE = "com/anriku/sclib/widget" + File.separator;

    public static HashMap<String, String> sNeedReplaceViews = new HashMap<>();

    static {
        sNeedReplaceViews.put("TextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatTextView");
        sNeedReplaceViews.put("AppCompatTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatTextView");
        sNeedReplaceViews.put("ImageView", SC_APP_COMPAT_PACKAGE + "SCAppCompatImageView");
        sNeedReplaceViews.put("AppCompatImageView", SC_APP_COMPAT_PACKAGE + "SCAppCompatImageView");
        sNeedReplaceViews.put("Button", SC_APP_COMPAT_PACKAGE + "SCAppCompatButton");
        sNeedReplaceViews.put("AppCompatButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatButton");
        sNeedReplaceViews.put("EditText", SC_APP_COMPAT_PACKAGE + "SCAppCompatEditText");
        sNeedReplaceViews.put("AppCompatEditText", SC_APP_COMPAT_PACKAGE + "SCAppCompatEditText");
        sNeedReplaceViews.put("Spinner", SC_APP_COMPAT_PACKAGE + "SCAppCompatSpinner");
        sNeedReplaceViews.put("AppCompatSpinner", SC_APP_COMPAT_PACKAGE + "SCAppCompatSpinner");
        sNeedReplaceViews.put("ImageButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatImageButton");
        sNeedReplaceViews.put("AppCompatImageButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatImageButton");
        sNeedReplaceViews.put("CheckBox", SC_APP_COMPAT_PACKAGE + "SCAppCompatCheckBox");
        sNeedReplaceViews.put("AppCompatCheckBox", SC_APP_COMPAT_PACKAGE + "SCAppCompatCheckBox");
        sNeedReplaceViews.put("RadioButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatRadioButton");
        sNeedReplaceViews.put("AppCompatRadioButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatRadioButton");
        sNeedReplaceViews.put("CheckedTextView", SC_APP_COMPAT_PACKAGE + "AppCompatCheckedTextView");
        sNeedReplaceViews.put("AppCompatCheckedTextView", SC_APP_COMPAT_PACKAGE + "AppCompatCheckedTextView");
        sNeedReplaceViews.put("AutoCompleteTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatAutoCompleteTextView");
        sNeedReplaceViews.put("AppCompatAutoCompleteTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatAutoCompleteTextView");
        sNeedReplaceViews.put("MultiAutoCompleteTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatMultiAutoCompleteTextView");
        sNeedReplaceViews.put("AppCompatMultiAutoCompleteTextView", SC_APP_COMPAT_PACKAGE + "SCAppCompatMultiAutoCompleteTextView");
        sNeedReplaceViews.put("RatingBar", SC_APP_COMPAT_PACKAGE + "SCAppCompatRatingBar");
        sNeedReplaceViews.put("AppCompatRatingBar", SC_APP_COMPAT_PACKAGE + "SCAppCompatRatingBar");
        sNeedReplaceViews.put("SeekBar", SC_APP_COMPAT_PACKAGE + "SCAppCompatSeekBar");
        sNeedReplaceViews.put("AppCompatSeekBar", SC_APP_COMPAT_PACKAGE + "SCAppCompatSeekBar");
        sNeedReplaceViews.put("ToggleButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatToggleButton");
        sNeedReplaceViews.put("AppCompatToggleButton", SC_APP_COMPAT_PACKAGE + "SCAppCompatToggleButton");
    }

}
