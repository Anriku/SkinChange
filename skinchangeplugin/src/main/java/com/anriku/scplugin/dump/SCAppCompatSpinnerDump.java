package com.anriku.scplugin.dump;

import com.anriku.scplugin.utils.PackageNameUtils;

import org.objectweb.asm.*;

import java.io.File;

import static org.objectweb.asm.Opcodes.T_INT;

/**
 * Created by anriku on 2019-10-10.
 */
public class SCAppCompatSpinnerDump {

    public static final String VIEW_PACKAGE_NAME = PackageNameUtils.sViewPackageName + File.separator;

    public static byte[] dump() {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, "com/anriku/sclib/widget/SCAppCompatSpinner", null, VIEW_PACKAGE_NAME + "AppCompatSpinner", new String[]{"com/anriku/sclib/widget/SkinChange"});

        cw.visitSource("SCAppCompatSpinner.java", null);

        cw.visitInnerClass("android/content/res/Resources$Theme", "android/content/res/Resources", "Theme", Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL);

        cw.visitInnerClass("android/R$attr", "android/R", "attr", Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC);

        {
            fv = cw.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL, "mSCBackgroundHelper", "Lcom/anriku/sclib/helpers/SCBackgroundHelper;", null, null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Landroid/content/Context;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(20, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitInsn(Opcodes.ACONST_NULL);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/anriku/sclib/widget/SCAppCompatSpinner", "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(21, l1);
            mv.visitInsn(Opcodes.RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lcom/anriku/sclib/widget/SCAppCompatSpinner;", null, l0, l2, 0);
            mv.visitLocalVariable("context", "Landroid/content/Context;", null, l0, l2, 1);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Landroid/content/Context;I)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(24, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitInsn(Opcodes.ACONST_NULL);
            mv.visitLdcInsn(new Integer(16842881));
            mv.visitVarInsn(Opcodes.ILOAD, 2);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/anriku/sclib/widget/SCAppCompatSpinner", "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(25, l1);
            mv.visitInsn(Opcodes.RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lcom/anriku/sclib/widget/SCAppCompatSpinner;", null, l0, l2, 0);
            mv.visitLocalVariable("context", "Landroid/content/Context;", null, l0, l2, 1);
            mv.visitLocalVariable("mode", "I", null, l0, l2, 2);
            mv.visitMaxs(5, 3);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(28, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitLdcInsn(new Integer(16842881));
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/anriku/sclib/widget/SCAppCompatSpinner", "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(29, l1);
            mv.visitInsn(Opcodes.RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lcom/anriku/sclib/widget/SCAppCompatSpinner;", null, l0, l2, 0);
            mv.visitLocalVariable("context", "Landroid/content/Context;", null, l0, l2, 1);
            mv.visitLocalVariable("attrs", "Landroid/util/AttributeSet;", null, l0, l2, 2);
            mv.visitMaxs(4, 3);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(32, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitInsn(Opcodes.ICONST_M1);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/anriku/sclib/widget/SCAppCompatSpinner", "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(33, l1);
            mv.visitInsn(Opcodes.RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lcom/anriku/sclib/widget/SCAppCompatSpinner;", null, l0, l2, 0);
            mv.visitLocalVariable("context", "Landroid/content/Context;", null, l0, l2, 1);
            mv.visitLocalVariable("attrs", "Landroid/util/AttributeSet;", null, l0, l2, 2);
            mv.visitLocalVariable("defStyleAttr", "I", null, l0, l2, 3);
            mv.visitMaxs(5, 4);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(36, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitVarInsn(Opcodes.ILOAD, 4);
            mv.visitInsn(Opcodes.ACONST_NULL);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/anriku/sclib/widget/SCAppCompatSpinner", "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;IILandroid/content/res/Resources$Theme;)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(37, l1);
            mv.visitInsn(Opcodes.RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lcom/anriku/sclib/widget/SCAppCompatSpinner;", null, l0, l2, 0);
            mv.visitLocalVariable("context", "Landroid/content/Context;", null, l0, l2, 1);
            mv.visitLocalVariable("attrs", "Landroid/util/AttributeSet;", null, l0, l2, 2);
            mv.visitLocalVariable("defStyleAttr", "I", null, l0, l2, 3);
            mv.visitLocalVariable("mode", "I", null, l0, l2, 4);
            mv.visitMaxs(6, 5);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;IILandroid/content/res/Resources$Theme;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(40, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitVarInsn(Opcodes.ILOAD, 4);
            mv.visitVarInsn(Opcodes.ALOAD, 5);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, VIEW_PACKAGE_NAME + "AppCompatSpinner", "<init>", "(Landroid/content/Context;Landroid/util/AttributeSet;IILandroid/content/res/Resources$Theme;)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(42, l1);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitTypeInsn(Opcodes.NEW, "com/anriku/sclib/helpers/SCBackgroundHelper");
            mv.visitInsn(Opcodes.DUP);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/anriku/sclib/helpers/SCBackgroundHelper", "<init>", "(Landroid/view/View;)V", false);
            mv.visitFieldInsn(Opcodes.PUTFIELD, "com/anriku/sclib/widget/SCAppCompatSpinner", "mSCBackgroundHelper", "Lcom/anriku/sclib/helpers/SCBackgroundHelper;");
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(44, l2);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, "com/anriku/sclib/widget/SCAppCompatSpinner", "mSCBackgroundHelper", "Lcom/anriku/sclib/helpers/SCBackgroundHelper;");
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/anriku/sclib/helpers/SCBackgroundHelper", "loadFromAttributes", "(Landroid/util/AttributeSet;I)V", false);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(45, l3);
            mv.visitInsn(Opcodes.RETURN);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLocalVariable("this", "Lcom/anriku/sclib/widget/SCAppCompatSpinner;", null, l0, l4, 0);
            mv.visitLocalVariable("context", "Landroid/content/Context;", null, l0, l4, 1);
            mv.visitLocalVariable("attrs", "Landroid/util/AttributeSet;", null, l0, l4, 2);
            mv.visitLocalVariable("defStyleAttr", "I", null, l0, l4, 3);
            mv.visitLocalVariable("mode", "I", null, l0, l4, 4);
            mv.visitLocalVariable("popupTheme", "Landroid/content/res/Resources$Theme;", null, l0, l4, 5);
            mv.visitMaxs(6, 6);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "setBackgroundResource", "(I)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(49, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, "com/anriku/sclib/widget/SCAppCompatSpinner", "mSCBackgroundHelper", "Lcom/anriku/sclib/helpers/SCBackgroundHelper;");
            mv.visitInsn(Opcodes.ICONST_1);
            mv.visitIntInsn(Opcodes.NEWARRAY, T_INT);
            mv.visitInsn(Opcodes.DUP);
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitVarInsn(Opcodes.ILOAD, 1);
            mv.visitInsn(Opcodes.IASTORE);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/anriku/sclib/helpers/SCBackgroundHelper", "recordAndReplaceResIds", "([I)[I", false);
            mv.visitVarInsn(Opcodes.ASTORE, 2);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(50, l1);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            Label l2 = new Label();
            mv.visitJumpInsn(Opcodes.IFNULL, l2);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(51, l3);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitInsn(Opcodes.IALOAD);
            mv.visitVarInsn(Opcodes.ISTORE, 1);
            mv.visitLabel(l2);
            mv.visitLineNumber(53, l2);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"[I"}, 0, null);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(Opcodes.ILOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, VIEW_PACKAGE_NAME + "AppCompatSpinner", "setBackgroundResource", "(I)V", false);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(54, l4);
            mv.visitInsn(Opcodes.RETURN);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLocalVariable("this", "Lcom/anriku/sclib/widget/SCAppCompatSpinner;", null, l0, l5, 0);
            mv.visitLocalVariable("resId", "I", null, l0, l5, 1);
            mv.visitLocalVariable("newResIds", "[I", null, l1, l5, 2);
            mv.visitMaxs(5, 3);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "applySkinChange", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(58, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, "com/anriku/sclib/widget/SCAppCompatSpinner", "mSCBackgroundHelper", "Lcom/anriku/sclib/helpers/SCBackgroundHelper;");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/anriku/sclib/helpers/SCBackgroundHelper", "applySkinChange", "()V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(59, l1);
            mv.visitInsn(Opcodes.RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "Lcom/anriku/sclib/widget/SCAppCompatSpinner;", null, l0, l2, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
