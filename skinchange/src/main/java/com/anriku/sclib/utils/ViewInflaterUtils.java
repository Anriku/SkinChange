package com.anriku.sclib.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.anriku.sclib.widget.SCAutoCompleteTextView;
import com.anriku.sclib.widget.SCButton;
import com.anriku.sclib.widget.SCCheckBox;
import com.anriku.sclib.widget.SCCheckedTextView;
import com.anriku.sclib.widget.SCEditText;
import com.anriku.sclib.widget.SCImageButton;
import com.anriku.sclib.widget.SCImageView;
import com.anriku.sclib.widget.SCMultiAutoCompleteTextView;
import com.anriku.sclib.widget.SCRadioButton;
import com.anriku.sclib.widget.SCRatingBar;
import com.anriku.sclib.widget.SCSeekBar;
import com.anriku.sclib.widget.SCSpinner;
import com.anriku.sclib.widget.SCTextView;
import com.anriku.sclib.widget.SCToggleButton;

/**
 * Created by anriku on 2019-10-12.
 */


public class ViewInflaterUtils {

    public static View createView(Context context, String name, AttributeSet attrs) {
        View view;
        switch (name) {
            case "TextView":
                view = createTextView(context, attrs);
                break;
            case "ImageView":
                view = createImageView(context, attrs);
                break;
            case "Button":
                view = createButton(context, attrs);
                break;
            case "EditText":
                view = createEditText(context, attrs);
                break;
            case "Spinner":
                view = createSpinner(context, attrs);
                break;
            case "ImageButton":
                view = createImageButton(context, attrs);
                break;
            case "CheckBox":
                view = createCheckBox(context, attrs);
                break;
            case "RadioButton":
                view = createRadioButton(context, attrs);
                break;
            case "CheckedTextView":
                view = createCheckedTextView(context, attrs);
                break;
            case "AutoCompleteTextView":
                view = createAutoCompleteTextView(context, attrs);
                break;
            case "MultiAutoCompleteTextView":
                view = createMultiAutoCompleteTextView(context, attrs);
                break;
            case "RatingBar":
                view = createRatingBar(context, attrs);
                break;
            case "SeekBar":
                view = createSeekBar(context, attrs);
                break;
            case "ToggleButton":
                view = createToggleButton(context, attrs);
                break;
            default:
                view = null;
        }
        return view;
    }

    private static TextView createTextView(Context context, AttributeSet attrs) {
        return new SCTextView(context, attrs);
    }

    private static ImageView createImageView(Context context, AttributeSet attrs) {
        return new SCImageView(context, attrs);
    }

    private static Button createButton(Context context, AttributeSet attrs) {
        return new SCButton(context, attrs);
    }

    private static EditText createEditText(Context context, AttributeSet attrs) {
        return new SCEditText(context, attrs);
    }

    private static Spinner createSpinner(Context context, AttributeSet attrs) {
        return new SCSpinner(context, attrs);
    }

    private static ImageButton createImageButton(Context context, AttributeSet attrs) {
        return new SCImageButton(context, attrs);
    }

    private static CheckBox createCheckBox(Context context, AttributeSet attrs) {
        return new SCCheckBox(context, attrs);
    }

    private static RadioButton createRadioButton(Context context, AttributeSet attrs) {
        return new SCRadioButton(context, attrs);
    }

    private static CheckedTextView createCheckedTextView(Context context, AttributeSet attrs) {
        return new SCCheckedTextView(context, attrs);
    }

    private static AutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new SCAutoCompleteTextView(context, attrs);
    }

    private static MultiAutoCompleteTextView createMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new SCMultiAutoCompleteTextView(context, attrs);
    }

    private static RatingBar createRatingBar(Context context, AttributeSet attrs) {
        return new SCRatingBar(context, attrs);
    }

    private static SeekBar createSeekBar(Context context, AttributeSet attrs) {
        return new SCSeekBar(context, attrs);
    }

    private static ToggleButton createToggleButton(Context context, AttributeSet attrs) {
        return new SCToggleButton(context, attrs);
    }
}