package com.anriku.scplugin.utils;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


/**
 * Created by anriku on 2019-10-14.
 */

public class VarInsnUtils {

    public static void visitLoadVarInsn(MethodVisitor mv, Type type, int index) {
        String desc = type.getDescriptor();
        switch (desc) {
            case "B":
            case "Z":
            case "C":
            case "S":
            case "I":
                mv.visitVarInsn(Opcodes.ILOAD, index);
                break;
            case "J":
                mv.visitVarInsn(Opcodes.LLOAD, index);
                break;
            case "F":
                mv.visitVarInsn(Opcodes.FLOAD, index);
            case "D":
                mv.visitVarInsn(Opcodes.DLOAD, index);
            default:
                mv.visitVarInsn(Opcodes.ALOAD, index);

        }
    }

    public static void visitReturnInsn(MethodVisitor mv, Type type) {
        String desc = type.getDescriptor();
        switch (desc) {
            case "B":
            case "Z":
            case "C":
            case "S":
            case "I":
                mv.visitInsn(Opcodes.IRETURN);
                break;
            case "J":
                mv.visitInsn(Opcodes.LRETURN);
                break;
            case "F":
                mv.visitInsn(Opcodes.FRETURN);
            case "D":
                mv.visitInsn(Opcodes.DRETURN);
            default:
                mv.visitInsn(Opcodes.ARETURN);

        }
    }

}
