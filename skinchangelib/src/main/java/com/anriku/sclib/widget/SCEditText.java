package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
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
                methodNameAndDescriptor = {"setTextAppearance", "(I)V"},
                parameterIndexes = {0}),
        @HelperClassToMethod(
                helperClass = SCTextAppearanceHelper.class,
                methodNameAndDescriptor = {"setTextAppearance", "(Landroid/content/Context;I)V"},
                parameterIndexes = {1}),
        @HelperClassToMethod(
                helperClass = SCRelativeCompoundDrawablesHelper.class,
                methodNameAndDescriptor = {"setCompoundDrawablesRelativeWithIntrinsicBounds", "(IIII)V"},
                parameterIndexes = {0, 1, 2, 3}),
        @HelperClassToMethod(
                helperClass = SCCompoundDrawablesHelper.class,
                methodNameAndDescriptor = {"setCompoundDrawablesWithIntrinsicBounds", "(IIII)V"},
                parameterIndexes = {0, 1, 2, 3}),
        @HelperClassToMethod(
                helperClass = SCBackgroundHelper.class,
                methodNameAndDescriptor = {"setBackgroundResource", "(I)V"},
                parameterIndexes = {0})
}, initConstructors = {
        @InitConstructor(
                constructorDescriptor = "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
                attrsIndex = 1,
                defStyleAttrIndex = 2
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
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void applySkinChange() {

    }
}
