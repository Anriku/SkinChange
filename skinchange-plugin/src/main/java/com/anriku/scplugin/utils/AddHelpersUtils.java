package com.anriku.scplugin.utils;

import com.anriku.scplugin.visitor.skinchangeviewannotation.AttrDefAttrDefResIndex;
import com.anriku.scplugin.visitor.VisitorVersion;
import com.anriku.scplugin.visitor.skinchangeviewannotation.HelperClassAndParameterIndexes;
import com.anriku.scplugin.visitor.skinchangeviewannotation.MethodInfo;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.Set;

import static org.objectweb.asm.Opcodes.T_INT;

/**
 * Created by anriku on 2019-10-12.
 */

public class AddHelpersUtils {

    /**
     * 进行SCHelper所有子类的域的添加
     */
    public static void addHelperFields(Set<Type> helperTypes, ClassVisitor cv) {
        for (Type type : helperTypes) {
            FieldVisitor fv = cv.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL,
                    type.getClassName().replace(".", "_"), type.getDescriptor(), null, null);
            fv.visitEnd();
        }
    }

    public static void secureRecordAndReplaceResIds(boolean staticMethod, MethodVisitor mv, String className,
                                                    Type type, int[] replaceParametersIndex, String methodDesc) {
        int newResIdsArrayIndex = LocalVarUtils.getLocalVarMin(staticMethod, methodDesc) + 1;
        int[] parametersIndex = LocalVarUtils.getAllParametersIndex(staticMethod, methodDesc);

        // 进行HelperUtils的secureRecordAndReplaceResIds方法的调用
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(Opcodes.GETFIELD, className, type.getClassName().replace(".", "_"), type.getDescriptor());
        IntLdcUtils.loadIntValue(mv, replaceParametersIndex.length);
        mv.visitIntInsn(Opcodes.NEWARRAY, T_INT);
        // 将所有需要替换资源的参数添加到数组中去
        for (int i = 0; i < replaceParametersIndex.length; i++) {
            mv.visitInsn(Opcodes.DUP);
            IntLdcUtils.loadIntValue(mv, i);
            mv.visitVarInsn(Opcodes.ILOAD, parametersIndex[replaceParametersIndex[i]]);
            mv.visitInsn(Opcodes.IASTORE);
        }
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/anriku/sclib/utils/HelperUtils", "secureRecordAndReplaceResIds", "(Lcom/anriku/sclib/helpers/SCHelper;[I)[I", false);
        mv.visitVarInsn(Opcodes.ASTORE, newResIdsArrayIndex);

        // 将HelperUtils的secureRecordAndReplaceResIds方法返回的结果赋值给参数
        for (int i = 0; i < replaceParametersIndex.length; i++) {
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitVarInsn(Opcodes.ALOAD, newResIdsArrayIndex);
            IntLdcUtils.loadIntValue(mv, i);
            mv.visitInsn(Opcodes.IALOAD);
            mv.visitVarInsn(Opcodes.ISTORE, parametersIndex[replaceParametersIndex[i]]);
        }
    }

    /**
     * 进行构造函数的添加
     *
     * @param cv               {@link ClassVisitor}
     * @param className        类的全限定名
     * @param superClassName   父类的全限定名
     * @param mHelpersType     所有该类使用的SCHelper的子类
     * @param initConstructors 所有需要添加的构造函数
     */
    public static void addInitConstructor(ClassVisitor cv, String className, String superClassName, Set<Type> mHelpersType,
                                          Map<MethodInfo, AttrDefAttrDefResIndex> initConstructors) {
        Set<Map.Entry<MethodInfo, AttrDefAttrDefResIndex>> entries = initConstructors.entrySet();
        for (Map.Entry<MethodInfo, AttrDefAttrDefResIndex> entry : entries) {
            MethodInfo methodInfo = entry.getKey();

            String desc = methodInfo.getDesc();
            AttrDefAttrDefResIndex attrDefAttrDefResIndex = entry.getValue();
            Type[] parametersType = Type.getArgumentTypes(desc);
            int[] parametersIndex = LocalVarUtils.getAllParametersIndex(false, desc);

            MethodVisitor mv = cv.visitMethod(methodInfo.getAccess(), methodInfo.getName(), desc, methodInfo.getSignature(), methodInfo.getExceptions());
            mv.visitCode();
            // 调用父类的构造方法
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            for (int i = 0; i < parametersType.length; i++) {
                Type type = parametersType[i];
                VarInsnUtils.visitLoadVarInsn(mv, type, parametersIndex[i]);
            }
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, superClassName, "<init>", desc, false);
            // 对所有的SCHelper的子类进行初始化
            for (Type type : mHelpersType) {
                InitHelperMethodAdapter.initHelper(mv, type, className,attrDefAttrDefResIndex);
            }

            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitInsn(Opcodes.RETURN);

            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

    public static void addMethod(ClassVisitor cv, String className, String superClassName,
                                 Map<MethodInfo, HelperClassAndParameterIndexes> methodsNeedHandle) {
        Set<Map.Entry<MethodInfo, HelperClassAndParameterIndexes>> entries = methodsNeedHandle.entrySet();
        for (Map.Entry<MethodInfo, HelperClassAndParameterIndexes> entry : entries) {
            MethodInfo methodInfo = entry.getKey();
            if (methodInfo == null) {
                continue;
            }
            String name = methodInfo.getName();
            String desc = methodInfo.getDesc();
            Type[] parametersType = Type.getArgumentTypes(desc);
            int[] parametersIndex = LocalVarUtils.getAllParametersIndex(false, desc);
            Type returnType = Type.getReturnType(desc);

            HelperClassAndParameterIndexes helperClassAndParameterIndexes = entry.getValue();
            Type helperType = helperClassAndParameterIndexes.getHelperClassType();
            int[] replaceParametersIndex = helperClassAndParameterIndexes.getParameterIndexes();

            // 添加方法
            MethodVisitor mv = cv.visitMethod(methodInfo.getAccess(), name, desc, methodInfo.getSignature(), methodInfo.getExceptions());
            secureRecordAndReplaceResIds(false, mv, className, helperType, replaceParametersIndex, desc);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            for (int i = 0; i < parametersType.length; i++) {
                Type type = parametersType[i];
                VarInsnUtils.visitLoadVarInsn(mv, type, parametersIndex[i]);
            }
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, superClassName, name, desc, false);
            if (returnType.getDescriptor().equals("V")) {
                Label l2 = new Label();
                mv.visitLabel(l2);
                mv.visitInsn(Opcodes.RETURN);
            } else {
                VarInsnUtils.visitReturnInsn(mv, returnType);
            }

            Label l3 = new Label();
            mv.visitLabel(l3);

            String returnTypeDesc = returnType.getDescriptor();
            int maxStack = 1;
            if (returnTypeDesc.equals("D") || returnTypeDesc.equals("J")) {
                maxStack = 2;
            }

            mv.visitMaxs(maxStack, LocalVarUtils.getLocalVarMin(false, desc) + 1);
            mv.visitEnd();
        }
    }

    /**
     * 对View的applySkinChange方法进行调用所有SCHelper子类的applyChange的方法的处理
     */
    public static class ApplySkinChangeMethodAdapter extends MethodVisitor {

        private String mViewClassName;
        private Set<Type> mHelperTypes;

        public ApplySkinChangeMethodAdapter(MethodVisitor mv, String viewClassName, Set<Type> helperTypes) {
            super(VisitorVersion.VERSION, mv);
            mViewClassName = viewClassName;
            mHelperTypes = helperTypes;
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.RETURN) {
                applySkinChange(mv, mViewClassName, mHelperTypes);
            }
            super.visitInsn(opcode);
        }

        public static void applySkinChange(MethodVisitor mv, String viewClassName, Set<Type> helperTypes) {
            for (Type type : helperTypes) {
                Label l0 = new Label();
                mv.visitLabel(l0);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(Opcodes.GETFIELD, viewClassName, type.getClassName().replace(".", "_"), type.getDescriptor());
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, type.getInternalName(), "applySkinChange", "()V", false);
            }
        }
    }

    /**
     * 用于在View的构造器中进行SCHelper所有子类的初始化
     */
    public static class InitHelperMethodAdapter extends MethodVisitor {

        private Set<Type> mHelperTypes;
        private String mClassName;
        private AttrDefAttrDefResIndex mAttrDefAttrDefResIndex;

        public InitHelperMethodAdapter(MethodVisitor mv, String className, Set<Type> helperTypes, AttrDefAttrDefResIndex attrDefAttrDefResIndex) {
            super(VisitorVersion.VERSION, mv);
            mClassName = className;
            mHelperTypes = helperTypes;
            mAttrDefAttrDefResIndex = attrDefAttrDefResIndex;
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.RETURN) {
                for (Type type : mHelperTypes) {
                    initHelper(mv, type, mClassName, mAttrDefAttrDefResIndex);
                }
            }
            super.visitInsn(opcode);
        }

        public static void initHelper(MethodVisitor mv, Type type, String className, AttrDefAttrDefResIndex attrDefAttrDefResIndex) {
            String helperInternalName = type.getInternalName();
            String fieldName = type.getClassName().replace(".", "_");
            String helperDescriptor = type.getDescriptor();

            // new SCHelper子类
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitTypeInsn(Opcodes.NEW, helperInternalName);
            mv.visitInsn(Opcodes.DUP);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, helperInternalName, "<init>", "(Landroid/view/View;)V", false);
            mv.visitFieldInsn(Opcodes.PUTFIELD, className, fieldName, helperDescriptor);

            // 调用SCHelper子类的loadFromAttributes方法
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, className, fieldName, helperDescriptor);
            // attrs变量加入操作数栈
            mv.visitVarInsn(Opcodes.ALOAD, attrDefAttrDefResIndex.getAttrsIndex() + 1);

            // defStyleAttr变量加入操作数栈，如果没有该变量就行将0加入操作数栈
            int defStyleAttrIndex = attrDefAttrDefResIndex.getDefStyleAttrIndex();
            if (defStyleAttrIndex != AttrDefAttrDefResIndex.INVALID_INDEX) {
                mv.visitVarInsn(Opcodes.ILOAD, defStyleAttrIndex + 1);
            } else {
                IntLdcUtils.loadIntValue(mv, 0);
            }

            int defStyleResIndex = attrDefAttrDefResIndex.getDefStyleResIndex();
            if (defStyleResIndex != AttrDefAttrDefResIndex.INVALID_INDEX) {
                mv.visitVarInsn(Opcodes.ILOAD, defStyleResIndex + 1);
            } else {
                IntLdcUtils.loadIntValue(mv, 0);
            }

            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, helperInternalName, "loadFromAttributes", "(Landroid/util/AttributeSet;II)V", false);
        }
    }

    /**
     * 给指定的方法执行SCHelper子类的recordAndReplaceResIds方法进行资源的替换
     */
    public static class ResourceReplaceMethodAdapter extends MethodVisitor {

        private boolean mStaticMethod;
        private String mViewClassName;
        private HelperClassAndParameterIndexes mHelperClassAndParameterIndexes;
        private String mMethodDesc;


        public ResourceReplaceMethodAdapter(MethodVisitor mv, boolean staticMethod, String viewClassName,
                                            HelperClassAndParameterIndexes helperClassAndParameterIndexes, String methodDesc) {
            super(VisitorVersion.VERSION, mv);
            mStaticMethod = staticMethod;
            mViewClassName = viewClassName;
            mHelperClassAndParameterIndexes = helperClassAndParameterIndexes;
            mMethodDesc = methodDesc;
        }

        @Override
        public void visitCode() {
            secureRecordAndReplaceResIds(mStaticMethod, mv, mViewClassName, mHelperClassAndParameterIndexes.getHelperClassType(),
                    mHelperClassAndParameterIndexes.getParameterIndexes(), mMethodDesc);
            super.visitCode();
        }
    }
}
