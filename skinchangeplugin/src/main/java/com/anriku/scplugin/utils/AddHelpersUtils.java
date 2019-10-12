package com.anriku.scplugin.utils;

import com.anriku.scplugin.utils.ConstructorUtils;
import com.anriku.scplugin.utils.IntLdcUtils;
import com.anriku.scplugin.visitor.VisitorVersion;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.List;

import static org.objectweb.asm.Opcodes.T_INT;

/**
 * Created by anriku on 2019-10-12.
 */

public class AddHelpersUtils {

    public static void addHelperFields(List<Type> helperTypes, ClassVisitor cv) {
        int size = helperTypes.size();
        for (int i = 0; i < size; i++) {
            Type type = helperTypes.get(i);
            FieldVisitor fv = cv.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL,
                    type.getClassName().replace(".", "_"), type.getDescriptor(), null, null);
            fv.visitEnd();
        }
    }

    public static void secureRecordAndReplaceResIds(boolean staticMethod, MethodVisitor mv, String className, Type type, int[] parameterIndexes, int parameterCount) {
        int arrayIndex = parameterCount + 1;

        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(Opcodes.GETFIELD, className, type.getClassName().replace(".", "_"), type.getDescriptor());
        IntLdcUtils.loadIntValue(mv, parameterIndexes.length);
        mv.visitIntInsn(Opcodes.NEWARRAY, T_INT);

        for (int i = 0; i < parameterIndexes.length; i++) {
            mv.visitInsn(Opcodes.DUP);
            IntLdcUtils.loadIntValue(mv, i);
            int localIndex = parameterIndexes[i];
            if (!staticMethod) {
                localIndex++;
            }
            mv.visitVarInsn(Opcodes.ILOAD, localIndex);
            mv.visitInsn(Opcodes.IASTORE);
        }

        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/anriku/sclib/utils/HelperUtils", "secureRecordAndReplaceResIds", "(Lcom/anriku/sclib/helpers/SCHelper;[I)[I", false);
        mv.visitVarInsn(Opcodes.ASTORE, arrayIndex);
        Label l1 = new Label();
        mv.visitLabel(l1);

        for (int i = 0; i < parameterIndexes.length; i++) {
            mv.visitVarInsn(Opcodes.ALOAD, arrayIndex);
            IntLdcUtils.loadIntValue(mv, i);
            mv.visitInsn(Opcodes.IALOAD);

            int localIndex = parameterIndexes[i];
            if (!staticMethod) {
                localIndex++;
            }
            mv.visitVarInsn(Opcodes.ISTORE, localIndex);
        }
    }

    public static void applySkinChange(MethodVisitor mv, String viewClassName, List<Type> helperTypes) {
        int size = helperTypes.size();
        for (int i = 0; i < size; i++) {
            Type type = helperTypes.get(i);

            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, viewClassName, type.getClassName().replace(".", "_"), type.getDescriptor());
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type.getInternalName(), "applySkinChange", "()V", false);
        }
    }

    public static class ApplySkinChangeMethodAdapter extends MethodVisitor {

        private String mViewClassName;
        private List<Type> mHelperTypes;

        public ApplySkinChangeMethodAdapter(MethodVisitor mv, String viewClassName, List<Type> helpertypes) {
            super(VisitorVersion.VERSION, mv);
            mViewClassName = viewClassName;
            mHelperTypes = helpertypes;
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.RETURN) {
                applySkinChange(mv, mViewClassName, mHelperTypes);
            }
            super.visitInsn(opcode);
        }
    }

    public static class InitHelperMethodAdapter extends MethodVisitor {

        private boolean mInvokeAnotherAttrConstructor;
        private List<Type> mHelperTypes;
        private String mClassName;

        public InitHelperMethodAdapter(MethodVisitor mv, String className, List<Type> helperTypes) {
            super(VisitorVersion.VERSION, mv);
            mClassName = className;
            mHelperTypes = helperTypes;
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (mClassName.equals(owner) && name.equals("<init>") && ConstructorUtils.CONSTRUCTORS_DESCRIPTOR.contains(desc)) {
                mInvokeAnotherAttrConstructor = true;
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.RETURN && !mInvokeAnotherAttrConstructor) {
                int size = mHelperTypes.size();
                for (int i = 0; i < size; i++) {
                    Type type = mHelperTypes.get(i);
                    initHelper(type);
                }
            }
            super.visitInsn(opcode);
        }


        private void initHelper(Type type) {
            String helperInternalName = type.getInternalName();
            String fieldName = type.getClassName().replace(".", "_");
            String helperDescriptor = type.getDescriptor();

            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitTypeInsn(Opcodes.NEW, helperInternalName);
            mv.visitInsn(Opcodes.DUP);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, helperInternalName, "<init>", "(Landroid/view/View;)V", false);
            mv.visitFieldInsn(Opcodes.PUTFIELD, mClassName, fieldName, helperDescriptor);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, mClassName, fieldName, helperDescriptor);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, helperInternalName, "loadFromAttributes", "(Landroid/util/AttributeSet;I)V", false);
        }
    }
}
