package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.Method;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCRelativeCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCTextAppearanceHelper;

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
                        desc = "(Landroid/content/Context;Landroid/util/AttributeSet;I)V"
                ),
                attrsIndex = 1,
                defStyleAttrIndex = 2
        )
})
public class SCAppCompactButton extends AppCompatButton implements SkinChange {

    public SCAppCompactButton(Context context) {
        this(context, null);
    }

    public SCAppCompactButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public SCAppCompactButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void applySkinChange() {

    }
}
