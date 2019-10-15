package com.anriku.scplugin.visitor;

import com.anriku.scplugin.utils.Constants;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 对继承自SDK中对Activity进行LayoutInflater的自定义Factory的设置。
 * <p>
 * Created by anriku on 2019-10-13.
 */
public class ActivityVisitor extends ClassVisitor {

    public static final String SDK_ACTIVITY = "android/app/Activity";

    // 是否此类是Activity作为的父类。
    private boolean mActivityIsSuperClass;
    private boolean mSetFactorySuccessfully;
    private String mClassName;

    public static byte[] getHandleBytes(byte[] originBytes) {
        ClassReader reader = new ClassReader(originBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new ActivityVisitor(writer);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    public ActivityVisitor(ClassVisitor cv) {
        super(VisitorVersion.VERSION, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mClassName = name;
        if (superName.equals(SDK_ACTIVITY)) {
            mActivityIsSuperClass = true;
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (mActivityIsSuperClass && name.equals("onCreate")) {
            mSetFactorySuccessfully = true;
            return methodVisitor == null ? null : new SetFactoryMethodAdapter(methodVisitor);
        }
        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        // 如果当前类继承自SDK中的Activity类并且该类没有onCreate方法，就重写其onCreate方法并进行Factory的设置。
        if (mActivityIsSuperClass && !mSetFactorySuccessfully) {
            {
                MethodVisitor mv = visitMethod(Opcodes.ACC_PROTECTED, "onCreate", "(Landroid/os/Bundle;)V", null, null);
                mv.visitCode();
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/view/LayoutInflater", "from", "(Landroid/content/Context;)Landroid/view/LayoutInflater;", false);
                mv.visitTypeInsn(Opcodes.NEW, Constants.CUSTOM_FACTORY);
                mv.visitInsn(Opcodes.DUP);
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Constants.CUSTOM_FACTORY, "<init>", "()V", false);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, Constants.SC_LAYOUT_INFLATER_COMPACT, "setFactory2", "(Landroid/view/LayoutInflater;Landroid/view/LayoutInflater$Factory2;)V", false);
                Label l1 = new Label();
                mv.visitLabel(l1);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitVarInsn(Opcodes.ALOAD, 1);
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "android/app/Activity", "onCreate", "(Landroid/os/Bundle;)V", false);
                Label l2 = new Label();
                mv.visitLabel(l2);
                mv.visitInsn(Opcodes.RETURN);
                Label l3 = new Label();
                mv.visitLabel(l3);
                mv.visitLocalVariable("this", mClassName, null, l0, l3, 0);
                mv.visitLocalVariable("savedInstanceState", "Landroid/os/Bundle;", null, l0, l3, 1);
                mv.visitMaxs(3, 2);
                mv.visitEnd();
            }
        }
        super.visitEnd();
    }

    public static class SetFactoryMethodAdapter extends MethodVisitor {

        public SetFactoryMethodAdapter(MethodVisitor mv) {
            super(VisitorVersion.VERSION, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/view/LayoutInflater", "from", "(Landroid/content/Context;)Landroid/view/LayoutInflater;", false);
            mv.visitTypeInsn(Opcodes.NEW, Constants.CUSTOM_FACTORY);
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Constants.CUSTOM_FACTORY, "<init>", "()V", false);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, Constants.SC_LAYOUT_INFLATER_COMPACT, "setFactory2", "(Landroid/view/LayoutInflater;Landroid/view/LayoutInflater$Factory2;)V", false);
        }
    }
}
