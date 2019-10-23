package com.anriku.sclib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by anriku on 2019-10-06.
 */
@Retention(CLASS)
@Target({TYPE})
public @interface SkinChangeView {

    HelperClassToMethod[] helperClassToMethodName();

    InitConstructor[] initConstructors();

}
