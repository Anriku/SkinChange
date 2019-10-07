package com.anriku.sclib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by anriku on 2019-10-06.
 */
@Retention(CLASS)
@Target({PARAMETER, METHOD, LOCAL_VARIABLE, FIELD})
public @interface SkinChangeFunction {

    // 继承自SCHelper的子类的全限定名
    Class helperClassWholeName();

    // 需要作为SCHelper的子类的onResourcesSet方法的参数在方法中的index
    int[] parameterIndexes();
}
