package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCImageHelper;

/**
 * Created by anriku on 2019-10-07.
 */

@SuppressLint("AppCompatCustomView")
@SkinChangeView(helperClassToMethodName = {
        @HelperClassToMethod(
                helperClass = SCBackgroundHelper.class,
                methodNameAndDescriptor = {"setBackgroundResource", "(I)V"},
                parameterIndexes = {0}),
        @HelperClassToMethod(
                helperClass = SCImageHelper.class,
                methodNameAndDescriptor = {"setImageResource", "(I)V"},
                parameterIndexes = {0}),
}, initConstructors = {
        @InitConstructor(
                constructorDescriptor = "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
                attrsIndex = 1,
                defStyleAttrIndex = 2
        )
})
public class SCImageView extends ImageView implements SkinChange {

    public SCImageView(Context context) {
        this(context, null);
    }

    public SCImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void applySkinChange() {

    }
}
