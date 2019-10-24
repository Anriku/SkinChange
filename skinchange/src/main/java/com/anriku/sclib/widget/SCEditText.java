package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.Method;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCRelativeCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCTextAppearanceHelper;

/**
 * Created by anriku on 2019-10-12.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeView(helperClassToMethodName = {
        @HelperClassToMethod(
                helperClass = SCTextAppearanceHelper.class,
                method = @Method(
                        name = "setTextAppearance",
                        desc = "(I)V"
                ),
                parameterIndexes = {0}),
        @HelperClassToMethod(
                helperClass = SCTextAppearanceHelper.class,
                method = @Method(
                        name = "setTextAppearance",
                        desc = "(Landroid/content/Context;I)V"
                ),
                parameterIndexes = {1}),
        @HelperClassToMethod(
                helperClass = SCRelativeCompoundDrawablesHelper.class,
                method = @Method(
                        name = "setCompoundDrawablesRelativeWithIntrinsicBounds",
                        desc = "(IIII)V"
                ),
                parameterIndexes = {0, 1, 2, 3}),
        @HelperClassToMethod(
                helperClass = SCCompoundDrawablesHelper.class,
                method = @Method(
                        name = "setCompoundDrawablesWithIntrinsicBounds",
                        desc = "(IIII)V"
                ),
                parameterIndexes = {0, 1, 2, 3}),
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
                        desc = "(Landroid/content/Context;Landroid/util/AttributeSet;II)V"
                ),
                attrsIndex = 1,
                defStyleAttrIndex = 2,
                defStyleResIndex = 3
        )
})
public class SCEditText extends EditText implements SkinChange {

    public SCEditText(Context context) {
        this(context, null);
    }

    public SCEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public SCEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("NewApi")
    public SCEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void applySkinChange() {

    }
}
