package com.anriku.scplugin.visitor;

import com.anriku.scplugin.dump.RMapsDump;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;

/**
 * 对ResUtils进行获取各个资源的方法进行更改
 * <p>
 * Created by anriku on 2019-10-07.
 */
public class ResUtilsVisitor extends ClassVisitor {

    public static final String RMAPS_DRAWABLE = RMapsDump.RMAPS_PACKAGE.replace(".", File.separator) + File.separator
            + RMapsDump.RMAPS + "_drawable";
    public static final String RMAPS_COLOR = RMapsDump.RMAPS_PACKAGE.replace(".", File.separator) + File.separator
            + RMapsDump.RMAPS + "_color";
    public static final String RMAPS_STRING = RMapsDump.RMAPS_PACKAGE.replace(".", File.separator) + File.separator
            + RMapsDump.RMAPS + "_string";
    public static final String RMAPS_STYLE = RMapsDump.RMAPS_PACKAGE.replace(".", File.separator) + File.separator
            + RMapsDump.RMAPS + "_style";

    private String mResUtilsWholeName = "com/anriku/sclib/utils/ResUtils";

    public ResUtilsVisitor(ClassVisitor cv) {
        super(VisitorVersion.VERSION, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        mResUtilsWholeName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (methodVisitor == null) {
            return null;
        }
        if (name.equals("getNewDrawableResId")) {
            return new MethodAdapter(methodVisitor, RMAPS_DRAWABLE);
        } else if (name.equals("getNewColorResId")) {
            return new MethodAdapter(methodVisitor, RMAPS_COLOR);
        } else if (name.equals("getNewStringResId")) {
            return new MethodAdapter(methodVisitor, RMAPS_STRING);
        } else if (name.equals("getNewStyleResId")) {
            return new MethodAdapter(methodVisitor, RMAPS_STYLE);
        } else {
            return methodVisitor;
        }
    }

    class MethodAdapter extends MethodVisitor {

        private String mMapsClassName;

        public MethodAdapter(MethodVisitor mv, String mapsClassName) {
            super(VisitorVersion.VERSION, mv);
            mMapsClassName = mapsClassName;
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.IRETURN) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, mMapsClassName, "integerToString", "Ljava/util/HashMap;");
                mv.visitFieldInsn(Opcodes.GETSTATIC, mMapsClassName, "stringToInteger", "Ljava/util/HashMap;");
                mv.visitFieldInsn(Opcodes.GETSTATIC, mResUtilsWholeName, "sSkinSuffix", "Ljava/lang/String;");
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, mResUtilsWholeName, "getNewResId", "(ILjava/util/Map;Ljava/util/Map;Ljava/lang/String;)I", false);
            }
            super.visitInsn(opcode);
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(4, 1);
        }
    }
}
