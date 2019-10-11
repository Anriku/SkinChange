package com.anriku.scplugin.utils;

import java.util.HashSet;

/**
 * Created by anriku on 2019-10-11.
 */

public class ConstructorUtils {

    public static final String FIRST_CONSTRUCTOR_DESCRIPTOR = "(Landroid/content/Context;)V";
    public static final String SECOND_CONSTRUCTOR_DESCRIPTOR = "(Landroid/content/Context;Landroid/util/AttributeSet;)V";
    public static final String THIRD_CONSTRUCTOR_DESCRIPTOR = "(Landroid/content/Context;Landroid/util/AttributeSet;I)V";
    public static final String FORTH_CONSTRUCTOR_DESCRIPTOR = "(Landroid/content/Context;Landroid/util/AttributeSet;II)V";

    public static final HashSet<String> CONSTRUCTORS_DESCRIPTOR = new HashSet<>();

    static {
        CONSTRUCTORS_DESCRIPTOR.add(SECOND_CONSTRUCTOR_DESCRIPTOR);
        CONSTRUCTORS_DESCRIPTOR.add(THIRD_CONSTRUCTOR_DESCRIPTOR);
        CONSTRUCTORS_DESCRIPTOR.add(FORTH_CONSTRUCTOR_DESCRIPTOR);
    }

}
