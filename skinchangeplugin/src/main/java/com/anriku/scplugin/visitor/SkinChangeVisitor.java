package com.anriku.scplugin.visitor;

import com.anriku.scplugin.dump.ResUtilsDump;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.File;

/**
 * 进行资源获取的插桩
 * <p>
 * Created by anriku on 2019-09-13.
 */
public class SkinChangeVisitor extends ClassVisitor {

    private String mPackageName;

    public SkinChangeVisitor(ClassVisitor classVisitor, String packageName) {
        super(Opcodes.ASM6, classVisitor);
        mPackageName = packageName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("betawenbeta:visitMethod:" + name);
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        return methodVisitor == null ? null : new MethodAdapter(methodVisitor, Type.getArgumentTypes(desc),
                (access & Opcodes.ACC_STATIC) != 0);
    }

    public class MethodAdapter extends MethodVisitor {

        private Type[] mParameterTypes;
        private String[] mParameterAnnotationDesc;
        private boolean[] mNeedHook;
        private boolean mStaticClass;

        public MethodAdapter(MethodVisitor mv, Type[] parameterTypes, boolean staticClass) {
            super(Opcodes.ASM6, mv);
            mParameterTypes = parameterTypes;
            mStaticClass = staticClass;
            mNeedHook = new boolean[parameterTypes.length];
            mParameterAnnotationDesc = new String[parameterTypes.length];
        }

        @Override
        public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
            // 对方法参数的注解进行记录
            mNeedHook[parameter] = judgeNeedHook(desc);
            mParameterAnnotationDesc[parameter] = desc;
            return super.visitParameterAnnotation(parameter, desc, visible);
        }

        /**
         * 判断这个注解的参数是否是需要插桩。
         */
        private boolean judgeNeedHook(String desc) {
            if (desc.contains("DrawableRes")) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void visitCode() {
            insertCode();
            super.visitCode();
        }

        /**
         * 对需要插桩的参数进行插桩
         */
        private void insertCode() {
            for (int i = 0; i < mNeedHook.length; i++) {
                if (mNeedHook[i]) {
                    mv.visitVarInsn(Opcodes.ALOAD, 0);

                    int localVarIndex = i;
                    if (!mStaticClass) {
                        localVarIndex++;
                    }

                    mv.visitVarInsn(Opcodes.ILOAD, localVarIndex);

                    if (mParameterAnnotationDesc[i].contains("DrawableRes")) {
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                                mPackageName.replace(".", "/") + File.separator + ResUtilsDump.SIMPLE_NAME,
                                "getNewDrawableResId", "(I)I", false);
                    }

                    mv.visitVarInsn(Opcodes.ISTORE, localVarIndex);
                }
            }
        }


    }

}
