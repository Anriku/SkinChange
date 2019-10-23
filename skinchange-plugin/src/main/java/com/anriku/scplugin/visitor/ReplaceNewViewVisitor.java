package com.anriku.scplugin.visitor;

import com.anriku.scplugin.utils.ReplaceNewViewUtils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 1. new 原始View ---> new 代理View。
 * 只要不是在代理View构造器中new的原始View都会被替换为代理View。
 * 2. 将所有非代理View的父类为原始View替换为代理View
 * <p>
 * Created by anriku on 2019-10-14.
 */
public class ReplaceNewViewVisitor extends ClassVisitor {

    private String mClassName;

    public static byte[] getHandleBytes(byte[] originBytes) {
        ClassReader reader = new ClassReader(originBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new ReplaceNewViewVisitor(writer);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        mClassName = name;

        // 如果父类是需要替换成代理View的View，而且当前View不是代理View就将其父类设置为代理View
        String proxyViewName = ReplaceNewViewUtils.sNeedReplaceViews.get(superName);
        if (proxyViewName != null && !name.equals(proxyViewName)) {
            super.visit(version, access, name, signature, proxyViewName, interfaces);
        } else {
            super.visit(version, access, name, signature, superName, interfaces);
        }
    }

    public ReplaceNewViewVisitor(ClassVisitor cv) {
        super(VisitorVersion.VERSION, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        return methodVisitor == null ? null : new ViewReplaceMethodAdapter(methodVisitor, name);
    }

    public class ViewReplaceMethodAdapter extends MethodVisitor {

        private String mMethodName;

        public ViewReplaceMethodAdapter(MethodVisitor mv, String methodName) {
            super(VisitorVersion.VERSION, mv);
            mMethodName = methodName;
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode == Opcodes.NEW) {
                // 如果当前new的View需要代理并且当前View为该代理View就不替换，反正替换
                String proxyViewName = ReplaceNewViewUtils.sNeedReplaceViews.get(type);
                if (proxyViewName != null &&
                        (!proxyViewName.equals(mClassName) || !mMethodName.equals("<init>"))) {
                    super.visitTypeInsn(opcode, proxyViewName);
                } else {
                    super.visitTypeInsn(opcode, type);
                }
            } else {
                super.visitTypeInsn(opcode, type);
            }
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (opcode == Opcodes.INVOKESPECIAL) {
                String proxyViewName = ReplaceNewViewUtils.sNeedReplaceViews.get(owner);
                if (proxyViewName != null && name.equals("<init>") &&
                        (!proxyViewName.equals(mClassName) || !mMethodName.equals("<init>"))) {
                    super.visitMethodInsn(opcode, proxyViewName, name, desc, itf);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }
    }

}
