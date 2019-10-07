package com.anriku.sclib.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by anriku on 2019-10-06.
 */
@Retention(CLASS)
@Target({TYPE})
public @interface SkinChangeFunctionArray {

    // 声明某个类使用到的所有SCHelper的子类的全限定名
    Class[] helperClassesWholeName();

}
