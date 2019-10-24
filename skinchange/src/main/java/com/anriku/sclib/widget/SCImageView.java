package com.anriku.sclib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.Method;
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
                method = @Method(
                        name = "setBackgroundResource",
                        desc = "(I)V"
                ),
                parameterIndexes = {0}),
        @HelperClassToMethod(
                helperClass = SCImageHelper.class,
                method = @Method(
                        name = "setImageResource",
                        desc = "(I)V"
                ),
                parameterIndexes = {0}),
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
public class SCImageView extends ImageView implements SkinChange {

    public SCImageView(Context context) {
        this(context, null);
    }

    public SCImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("NewApi")
    public SCImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void applySkinChange() {

    }
}
