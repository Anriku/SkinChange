package com.anriku.sclib.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;

import com.anriku.sclib.widget.SCAppCompactButton;
import com.anriku.sclib.widget.SCAppCompactCheckBox;
import com.anriku.sclib.widget.SCAppCompatAutoCompleteTextView;
import com.anriku.sclib.widget.SCAppCompatCheckedTextView;
import com.anriku.sclib.widget.SCAppCompatEditText;
import com.anriku.sclib.widget.SCAppCompatImageButton;
import com.anriku.sclib.widget.SCAppCompatImageView;
import com.anriku.sclib.widget.SCAppCompatRadioButton;
import com.anriku.sclib.widget.SCAppCompatRatingBar;
import com.anriku.sclib.widget.SCAppCompatSeekBar;
import com.anriku.sclib.widget.SCAppCompatSpinner;
import com.anriku.sclib.widget.SCAppCompatTextView;
import com.anriku.sclib.widget.SCAppCompatToggleButton;
import com.anriku.sclib.widget.SCCompatMultiAutoCompleteTextView;

/**
 * Created by anriku on 2019-10-09.
 */

public class CreateViewUtils {

    public static View createView(Context context, String name, AttributeSet attrs) {
        int index = name.lastIndexOf(".");
        if (index != -1) {
            name = name.substring(index + 1);
        }
        View view;
        switch (name) {
            case "AppCompatTextView":
                view = createTextView(context, attrs);
                break;
            case "AppCompatImageView":
                view = createImageView(context, attrs);
                break;
            case "AppCompatButton":
                view = createButton(context, attrs);
                break;
            case "AppCompatEditText":
                view = createEditText(context, attrs);
                break;
            case "AppCompatSpinner":
                view = createSpinner(context, attrs);
                break;
            case "AppCompatImageButton":
                view = createImageButton(context, attrs);
                break;
            case "AppCompatCheckBox":
                view = createCheckBox(context, attrs);
                break;
            case "AppCompatRadioButton":
                view = createRadioButton(context, attrs);
                break;
            case "AppCompatCheckedTextView":
                view = createCheckedTextView(context, attrs);
                break;
            case "AppCompatAutoCompleteTextView":
                view = createAutoCompleteTextView(context, attrs);
                break;
            case "AppCompatMultiAutoCompleteTextView":
                view = createMultiAutoCompleteTextView(context, attrs);
                break;
            case "AppCompatRatingBar":
                view = createRatingBar(context, attrs);
                break;
            case "AppCompatSeekBar":
                view = createSeekBar(context, attrs);
                break;
            case "AppCompatToggleButton":
                view = createToggleButton(context, attrs);
                break;
            default:
                view = null;
        }
        return view;
    }

    private static AppCompatTextView createTextView(Context context, AttributeSet attrs) {
        return new SCAppCompatTextView(context, attrs);
    }

    private static AppCompatImageView createImageView(Context context, AttributeSet attrs) {
        return new SCAppCompatImageView(context, attrs);
    }

    private static AppCompatButton createButton(Context context, AttributeSet attrs) {
        return new SCAppCompactButton(context, attrs);
    }

    private static AppCompatEditText createEditText(Context context, AttributeSet attrs) {
        return new SCAppCompatEditText(context, attrs);
    }

    private static AppCompatSpinner createSpinner(Context context, AttributeSet attrs) {
        return new SCAppCompatSpinner(context, attrs);
    }

    private static AppCompatImageButton createImageButton(Context context, AttributeSet attrs) {
        return new SCAppCompatImageButton(context, attrs);
    }

    private static AppCompatCheckBox createCheckBox(Context context, AttributeSet attrs) {
        return new SCAppCompactCheckBox(context, attrs);
    }

    private static AppCompatRadioButton createRadioButton(Context context, AttributeSet attrs) {
        return new SCAppCompatRadioButton(context, attrs);
    }

    private static AppCompatCheckedTextView createCheckedTextView(Context context, AttributeSet attrs) {
        return new SCAppCompatCheckedTextView(context, attrs);
    }

    private static AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new SCAppCompatAutoCompleteTextView(context, attrs);
    }

    private static AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new SCCompatMultiAutoCompleteTextView(context, attrs);
    }

    private static AppCompatRatingBar createRatingBar(Context context, AttributeSet attrs) {
        return new SCAppCompatRatingBar(context, attrs);
    }

    private static AppCompatSeekBar createSeekBar(Context context, AttributeSet attrs) {
        return new SCAppCompatSeekBar(context, attrs);
    }

    private static AppCompatToggleButton createToggleButton(Context context, AttributeSet attrs) {
        return new SCAppCompatToggleButton(context, attrs);
    }
}
