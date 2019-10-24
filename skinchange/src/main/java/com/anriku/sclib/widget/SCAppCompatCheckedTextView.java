package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckedTextView;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.Method;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCRelativeCompoundDrawablesHelper;
import com.anriku.sclib.helpers.SCTextAppearanceHelper;

/**
 * Created by anriku on 2019-10-09.
 */
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
public class SCAppCompatCheckedTextView extends AppCompatCheckedTextView implements SkinChange {


    public SCAppCompatCheckedTextView(Context context) {
        this(context, null);
    }

    public SCAppCompatCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkedTextViewStyle);
    }

    public SCAppCompatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void applySkinChange() {

    }
}
