package com.anriku.scplugin.visitor;


import com.anriku.scplugin.utils.ReplaceNewViewUtils;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by anriku on 2019-10-09.
 */

public class NewViewVisitor extends ClassVisitor {

    public NewViewVisitor(ClassVisitor classVisitor) {
        super(VisitorVersion.VERSION, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        return methodVisitor == null ? null : new MethodAdapter(methodVisitor);
    }

    static class MethodAdapter extends MethodVisitor {

        public MethodAdapter(MethodVisitor mv) {
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
