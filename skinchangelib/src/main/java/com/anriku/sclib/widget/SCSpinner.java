package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;


/**
 * Created by anriku on 2019-10-12.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeView(helperClassToMethodName = {
        @HelperClassToMethod(
                helperClass = SCBackgroundHelper.class,
                methodNameAndDescriptor = {"setBackgroundResource", "(I)V"},
                parameterIndexes = {0})
}, initConstructors = {
        @InitConstructor(
                constructorDescriptor = "(Landroid/content/Context;Landroid/util/AttributeSet;II)V",
                attrsIndex = 1,
                defStyleAttrIndex = 2
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
        this(context, attrs, defStyleAttr, -1);
    }

    public SCSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    @Override
    public void applySkinChange() {

    }
}
