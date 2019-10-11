package com.anriku.scplugin.utils;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by anriku on 2019-10-12.
 */

public class IntLdcUtils {

    public static void loadIntValue(MethodVisitor mv, int value) {
        if (value >= -1 && value <= 5) {
            mv.visitInsn(value + 3);
        } else if (value >= -128 && value <= 127) {
            mv.visitIntInsn(Opcodes.BIPUSH, value);
        } else if (value >= -32768 && value <= 32767) {
            mv.visitIntInsn(Opcodes.SIPUSH, value);
        } else {
            mv.visitLdcInsn(value);
        }
    }

}
