package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.Method;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;


/**
 * Created by anriku on 2019-10-12.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeView(helperClassToMethodName = {
        @HelperClassToMethod(
                helperClass = SCBackgroundHelper.class,
                method = @Method(
                        name = "setBackgroundResource",
                        desc = "(I)V"
                ),
                parameterIndexes = {0})
}, initConstructors = {
        @InitConstructor(
                constructor = @Method(
                        name = "<init>",
                        desc = "(Landroid/content/Context;Landroid/util/AttributeSet;IIILandroid/content/res/Resources$Theme;)V"
                ),
                attrsIndex = 1,
                defStyleAttrIndex = 2,
                defStyleResIndex = 3
        )
})
public class SCSpinner extends Spinner implements SkinChange {

    public SCSpinner(Context context) {
        this(context, null);
    }

    public SCSpinner(Context context, int mode) {
        this(context, null, android.R.attr.spinnerStyle, mode);
    }

    public SCSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.spinnerStyle);
    }

    public SCSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0, -1);
    }

    public SCSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        this(context, attrs, defStyleAttr, 0, mode);
    }

    public SCSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        this(context, attrs, defStyleAttr, defStyleRes, mode, null);
    }

    @SuppressLint("NewApi")
    public SCSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
    }

    @Override
    public void applySkinChange() {

    }
}
