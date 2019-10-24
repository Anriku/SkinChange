package com.anriku.sclib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSpinner;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.Method;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;

/**
 * Created by anriku on 2019-10-09.
 */
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
                        desc = "(Landroid/content/Context;Landroid/util/AttributeSet;IILandroid/content/res/Resources$Theme;)V"
                ),
                attrsIndex = 1,
                defStyleAttrIndex = 2
        )
})
public class SCAppCompatSpinner extends AppCompatSpinner implements SkinChange {


    public SCAppCompatSpinner(Context context) {
        this(context, null);
    }

    public SCAppCompatSpinner(Context context, int mode) {
        this(context, null, android.R.attr.spinnerStyle, mode);
    }

    public SCAppCompatSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.spinnerStyle);
    }

    public SCAppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public SCAppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        this(context, attrs, defStyleAttr, mode, null);
    }

    public SCAppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    @Override
    public void applySkinChange() {

    }
}
