package com.anriku.scplugin.visitor;

import com.anriku.scplugin.utils.ReplaceNewViewUtils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 对代码中所有的new相应View的过程改成new代理View的过程。
 * <p>
 * Created by anriku on 2019-10-14.
 */
public class NewViewReplaceVisitor extends ClassVisitor {

    private boolean mWidget;

    public static byte[] getHandleBytes(byte[] originBytes) {
        ClassReader reader = new ClassReader(originBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new NewViewReplaceVisitor(writer);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        if (ReplaceNewViewUtils.sNeedReplaceViews.get(superName) != null) {
            mWidget = true;
        }
    }

    public NewViewReplaceVisitor(ClassVisitor cv) {
        super(VisitorVersion.VERSION, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (!mWidget) {
            return methodVisitor == null ? null : new ViewReplaceMethodAdapter(methodVisitor);
        }
        return methodVisitor;
    }

    public class ViewReplaceMethodAdapter extends MethodVisitor {

        public ViewReplaceMethodAdapter(MethodVisitor mv) {
            super(VisitorVersion.VERSION, mv);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode == Opcodes.NEW) {

                String wholeViewName = ReplaceNewViewUtils.sNeedReplaceViews.get(type);
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
                String wholeViewName = ReplaceNewViewUtils.sNeedReplaceViews.get(owner);
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
