package com.anriku.scplugin.visitor;


import com.anriku.scplugin.utils.ConstructorUtils;
import com.anriku.scplugin.utils.ReplaceNewViewUtils;
import com.anriku.scplugin.utils.AddHelpersUtils;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anriku on 2019-10-09.
 */

public class SkinChangeVisitor extends ClassVisitor {

    public static final String SKIN_CHANGE_FUNCTION_DESCRIPTOR = "Lcom/anriku/sclib/annotation/SkinChangeFunction;";
    public static final String SKIN_CHANGE_FUNCTION_ARRAY_DESCRIPTOR = "Lcom/anriku/sclib/annotation/SkinChangeFunctionArray;";
    private List<Type> mHelperTypes = new ArrayList<>();
    private boolean mFirstMethod = true;
    private String mClassName = "";
    private boolean activityIsSuperClass;

    public SkinChangeVisitor(ClassVisitor classVisitor) {
        super(VisitorVersion.VERSION, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        if (superName.equals("android/app/Activity")) {
            activityIsSuperClass = true;
        }
        mClassName = name;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible);
        if (desc.equals(SKIN_CHANGE_FUNCTION_ARRAY_DESCRIPTOR)) {
            return annotationVisitor == null ? null : new SkinChangeFunctionArrayAnnotationAdapter(annotationVisitor);
        } else {
            return annotationVisitor;
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (mFirstMethod && !mHelperTypes.isEmpty()) {
            AddHelpersUtils.addHelperFields(mHelperTypes, this);
            mFirstMethod = false;
        }
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);

        if (!mHelperTypes.isEmpty()) {
            if (name.equals("<init>") && ConstructorUtils.CONSTRUCTORS_DESCRIPTOR.contains(desc)) {
                return methodVisitor == null ? null : new AddHelpersUtils.InitHelperMethodAdapter(methodVisitor, mClassName, mHelperTypes);
            } else if (name.equals("applySkinChange")) {
                return methodVisitor == null ? null : new AddHelpersUtils.ApplySkinChangeMethodAdapter(methodVisitor, mClassName, mHelperTypes);
            }
        } else if (activityIsSuperClass && name.equals("onCreate")){
            return methodVisitor == null ? null : new SetFactoryMethodAdapter(methodVisitor);
        }
        return methodVisitor == null ? null : new SkinChangeMethodAdapter(methodVisitor,
                (access & Opcodes.ACC_STATIC) != 0, Type.getArgumentsAndReturnSizes(desc));
    }

    public static class SetFactoryMethodAdapter extends MethodVisitor {

        public SetFactoryMethodAdapter(MethodVisitor mv) {
            super(VisitorVersion.VERSION, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/view/LayoutInflater", "from", "(Landroid/content/Context;)Landroid/view/LayoutInflater;", false);
            mv.visitTypeInsn(Opcodes.NEW, "com/anriku/sclib/utils/CustomFactory");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/anriku/sclib/utils/CustomFactory", "<init>", "()V", false);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/anriku/sclib/utils/SCLayoutInflaterCompat", "setFactory2", "(Landroid/view/LayoutInflater;Landroid/view/LayoutInflater$Factory2;)V", false);
        }
    }

    public class SkinChangeMethodAdapter extends MethodVisitor {

        private Type mHelperClassWholeName;
        private int[] mParameterIndexes;
        private boolean mStaticClass;
        private int mParameterCount;

        public SkinChangeMethodAdapter(MethodVisitor mv, boolean staticClass, int parameterCount) {
            super(VisitorVersion.VERSION, mv);
            mStaticClass = staticClass;
            mParameterCount = parameterCount;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible);
            if (desc.equals(SKIN_CHANGE_FUNCTION_DESCRIPTOR)) {
                return annotationVisitor == null ? null : new SkinChangeFunctionAnnotationAdapter(annotationVisitor);
            }
            return annotationVisitor;
        }

        @Override
        public void visitCode() {
            super.visitCode();
            if (mHelperClassWholeName != null && mParameterIndexes != null) {
                AddHelpersUtils.secureRecordAndReplaceResIds(mStaticClass, mv, mClassName,
                        mHelperClassWholeName, mParameterIndexes, mParameterCount);
            }
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

        class SkinChangeFunctionAnnotationAdapter extends AnnotationVisitor {

            SkinChangeFunctionAnnotationAdapter(AnnotationVisitor av) {
                super(VisitorVersion.VERSION, av);
            }

            @Override
            public void visit(String name, Object value) {
                super.visit(name, value);
                if (name.equals("helperClassWholeName")) {
                    mHelperClassWholeName = (Type) value;
                } else if (name.equals("parameterIndexes")) {
                    mParameterIndexes = (int[]) value;
                }
            }
        }
    }


    class SkinChangeFunctionArrayAnnotationAdapter extends AnnotationVisitor {

        public SkinChangeFunctionArrayAnnotationAdapter(AnnotationVisitor av) {
            super(VisitorVersion.VERSION, av);
        }

        @Override
        public AnnotationVisitor visitArray(String name) {
            AnnotationVisitor annotationVisitor = super.visitArray(name);
            return annotationVisitor == null ? null : new HelperClassesWholeNameAnnotationAdapter(annotationVisitor);
        }
    }

    class HelperClassesWholeNameAnnotationAdapter extends AnnotationVisitor {

        public HelperClassesWholeNameAnnotationAdapter(AnnotationVisitor av) {
            super(VisitorVersion.VERSION, av);
        }

        @Override
        public void visit(String name, Object value) {
            super.visit(name, value);
            if (value instanceof Type) {
                mHelperTypes.add((Type) value);
            }
        }
    }
}
