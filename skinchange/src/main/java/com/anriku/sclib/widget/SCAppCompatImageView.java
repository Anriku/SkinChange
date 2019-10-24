package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.Method;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;
import com.anriku.sclib.helpers.SCImageHelper;

/**
 * Created by anriku on 2019-10-07.
 */
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
                        desc = "(Landroid/content/Context;Landroid/util/AttributeSet;I)V"
                ),
                attrsIndex = 1,
                defStyleAttrIndex = 2
        )
})
public class SCAppCompatImageView extends AppCompatImageView implements SkinChange {


    public SCAppCompatImageView(Context context) {
        this(context, null);
    }

    public SCAppCompatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCAppCompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void applySkinChange() {

    }
}
