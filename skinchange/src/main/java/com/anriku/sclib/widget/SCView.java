package com.anriku.sclib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.anriku.sclib.annotation.HelperClassToMethod;
import com.anriku.sclib.annotation.InitConstructor;
import com.anriku.sclib.annotation.SkinChangeView;
import com.anriku.sclib.helpers.SCBackgroundHelper;


/**
 * Created by anriku on 2019-10-05.
 */
@SkinChangeView(helperClassToMethodName = {
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
public class SCView extends View implements SkinChange {

    public SCView(Context context) {
        super(context);
    }

    public SCView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void applySkinChange() {

    }
}
