package com.anriku.scplugin.visitor;

import com.anriku.scplugin.utils.ReplaceNewViewUtils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 对代码中所有的new相应View的过程改成new代理View的过程。
 *
 * Created by anriku on 2019-10-14.
 */
public class NewViewReplaceVisitor extends ClassVisitor {

    public static byte[] getHandleBytes(byte[] originBytes) {
        ClassReader reader = new ClassReader(originBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new NewViewReplaceVisitor(writer);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    public NewViewReplaceVisitor(ClassVisitor cv) {
        super(VisitorVersion.VERSION, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        return methodVisitor == null ? null : new ViewReplaceMethodAdapter(methodVisitor);
    }

    public class ViewReplaceMethodAdapter extends MethodVisitor {

        public ViewReplaceMethodAdapter(MethodVisitor mv) {
            super(VisitorVersion.VERSION, mv);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode == Opcodes.NEW) {
                int index = type.lastIndexOf("/");
                String simpleType = type;
                if (index != -1) {
                    simpleType = simpleType.substring(index + 1);
                }

                String wholeViewName = ReplaceNewViewUtils.sNeedReplaceViews.get(simpleType);
                if (wholeViewName != null) {
                    super.visitTypeInsn(opcode, wholeViewName);
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
                int index = owner.lastIndexOf("/");
                String simpleType = owner;
                if (index != -1) {
                    simpleType = simpleType.substring(index + 1);
                }
                String wholeViewName = ReplaceNewViewUtils.sNeedReplaceViews.get(simpleType);
                if (wholeViewName != null && name.equals("<init>")) {
                    super.visitMethodInsn(opcode, wholeViewName, name, desc, itf);
                } else {
                    super.visitMethodInsn(opcode, owner, name, desc, itf);
                }
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }
    }

}
