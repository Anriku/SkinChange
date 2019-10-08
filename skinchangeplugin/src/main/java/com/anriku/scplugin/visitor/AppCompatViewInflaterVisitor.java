package com.anriku.scplugin.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;

/**
 * Created by anriku on 2019-10-08.
 */

public class AppCompatViewInflaterVisitor extends ClassVisitor {

    public static final String PACKAGE_NAME = "com.anriku.sclib.widget";

    public AppCompatViewInflaterVisitor(ClassVisitor cv) {
        super(VisitorVersion.VERSION, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (methodVisitor == null) {
            return null;
        }
        String prefix = PACKAGE_NAME.replace(".", "/") + File.separator + "SC";

        switch (name) {
            case "createTextView":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatTextView");
            case "createImageView":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatImageView");
            case "createButton":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatButton");
            case "createEditText":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatEditText");
            case "createSpinner":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatSpinner");
            case "createImageButton":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatImageButton");
            case "createCheckBox":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatCheckBox");
            case "createRadioButton":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatRadioButton");
            case "createCheckedTextView":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatCheckedTextView");
            case "createAutoCompleteTextView":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatAutoCompleteTextView");
            case "createMultiAutoCompleteTextView":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatMultiAutoCompleteTextView");
            case "createRatingBar":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatRatingBar");
            case "createSeekBar":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatSeekBar");
            case "createToggleButton":
                return new MethodAdapter(methodVisitor, prefix + "AppCompatToggleButton");
            case "createView":
                if (desc.equals("(Landroid/content/Context;Ljava/lang/String;Landroid/util/AttributeSet;)Landroid/view/View;")) {
                    return new CreateViewMethodAdapter(methodVisitor);
                } else {
                    return methodVisitor;
                }
            default:
                return methodVisitor;
        }
    }

    static class CreateViewMethodAdapter extends MethodVisitor {

        public CreateViewMethodAdapter(MethodVisitor mv) {
            super(VisitorVersion.VERSION, mv);
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.ACONST_NULL) {
                mv.visitVarInsn(Opcodes.ALOAD, 1);
                mv.visitVarInsn(Opcodes.ALOAD, 2);
                mv.visitVarInsn(Opcodes.ALOAD, 3);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/anriku/sclib/utils/AppComaptViewInflaterUtils", "createView", "(Landroid/content/Context;Ljava/lang/String;Landroid/util/AttributeSet;)Landroid/view/View;", false);
            } else {
                super.visitInsn(opcode);
            }
        }
    }

    static class MethodAdapter extends MethodVisitor {

        private String mType;

        public MethodAdapter(MethodVisitor mv, String type) {
            super(VisitorVersion.VERSION, mv);
            mType = type;
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            super.visitTypeInsn(opcode, mType);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            super.visitMethodInsn(opcode, mType, name, desc, itf);
        }
    }
}
