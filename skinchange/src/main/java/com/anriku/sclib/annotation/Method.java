package com.anriku.sclib.annotation;

import com.anriku.sclib.utils.SCOpcodes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by anriku on 2019-10-24.
 */
@Retention(CLASS)
@Target({PARAMETER, METHOD, LOCAL_VARIABLE, FIELD})
public @interface Method {

    int access() default SCOpcodes.ACC_PUBLIC;

    String name();

    String desc();

    String signature() default "";

    String[] exceptions() default {};


}
