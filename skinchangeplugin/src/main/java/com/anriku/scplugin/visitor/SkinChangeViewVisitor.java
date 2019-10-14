package com.anriku.scplugin.visitor;


import com.anriku.scplugin.utils.AddHelpersUtils;
import com.anriku.scplugin.utils.SCLibClass;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 对@SkinChangeView注解的View类进行处理。
 * <p>
 * Created by anriku on 2019-10-09.
 */
public class SkinChangeViewVisitor extends ClassVisitor {

    private Set<Type> mHelperTypes = new HashSet<>();
    // PS:key为方法的name + desc
    private Map<String, HelperClassAndParameterIndexes> mMethodsNeedHandle = new HashMap<>();
    // PS:key为构造器的desc
    private Map<String, AttrsAndDefStyleAttrIndex> mInitConstructors = new HashMap<>();
    // PS:key为方法的name + desc
    private Map<String, HelperClassAndParameterIndexes> mCopyMethodsNeedHandle = new HashMap<>();
    // PS:key为构造器的desc
    private Map<String, AttrsAndDefStyleAttrIndex> mCopyInitConstructors = new HashMap<>();

    private boolean mFirstMethod = true;
    private String mClassName;
    private String mSuperClassName;

    public static byte[] getHandleBytes(byte[] originBytes) {
        ClassReader reader = new ClassReader(originBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new SkinChangeViewVisitor(writer);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    public SkinChangeViewVisitor(ClassVisitor classVisitor) {
        super(VisitorVersion.VERSION, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mClassName = name;
        mSuperClassName = superName;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(desc, visible);
        if (desc.equals(SCLibClass.SKIN_CHANGE_VIEW_DESCRIPTOR)) {
            return annotationVisitor == null ? null : new SkinChangeViewAnnotationAdapter(annotationVisitor);
        }
        return annotationVisitor;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);

        if (mMethodsNeedHandle.isEmpty()) {
            return methodVisitor;
        }

        if (mFirstMethod) {
            AddHelpersUtils.addHelperFields(mHelperTypes, this);
            mCopyInitConstructors = new HashMap<>(mInitConstructors);
            mCopyMethodsNeedHandle = new HashMap<>(mMethodsNeedHandle);
            mFirstMethod = false;
        }

        if (name.equals("<init>") && mInitConstructors.get(desc) != null && !mHelperTypes.isEmpty()) {
            mCopyInitConstructors.remove(desc);
            return methodVisitor == null ? null : new AddHelpersUtils.InitHelperMethodAdapter
                    (methodVisitor, mClassName, mHelperTypes, mInitConstructors.get(desc));
        } else if (name.equals("applySkinChange") && !mHelperTypes.isEmpty()) {
            return methodVisitor == null ? null : new AddHelpersUtils.ApplySkinChangeMethodAdapter(methodVisitor, mClassName, mHelperTypes);
        } else if (mMethodsNeedHandle.get(name + desc) != null) {
            mCopyMethodsNeedHandle.remove(name + desc);
            return methodVisitor == null ? null : new AddHelpersUtils.ResourceReplaceMethodAdapter(methodVisitor, (access & Opcodes.ACC_STATIC) != 0,
                    mClassName, mMethodsNeedHandle.get(name + desc), desc);
        }

        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        if (!mCopyInitConstructors.isEmpty()) {
            AddHelpersUtils.addInitConstructor(cv, mClassName, mSuperClassName, mHelperTypes, mCopyInitConstructors);
        }
        if (!mCopyMethodsNeedHandle.isEmpty()) {
            AddHelpersUtils.addMethod(cv, mClassName, mSuperClassName, mCopyMethodsNeedHandle);
        }

        super.visitEnd();
    }

    /**
     * 对@SkinChangeView注解的处理
     */
    class SkinChangeViewAnnotationAdapter extends AnnotationVisitor {

        public static final String HELPER_CLASS_TO_METHOD_NAME = "helperClassToMethodName";
        public static final String INIT_CONSTRUCTORS = "initConstructors";

        public SkinChangeViewAnnotationAdapter(AnnotationVisitor av) {
            super(VisitorVersion.VERSION, av);
        }

        @Override
        public AnnotationVisitor visitArray(String name) {
            AnnotationVisitor annotationVisitor = super.visitArray(name);
            switch (name) {
                case HELPER_CLASS_TO_METHOD_NAME:
                    return annotationVisitor == null ? null : new HelperClassToMethodArrayAdapter(annotationVisitor);
                case INIT_CONSTRUCTORS:
                    return annotationVisitor == null ? null : new InitConstructorArrayAdapter(annotationVisitor);

            }

            return annotationVisitor;
        }
    }

    /**
     * 对@SkinChangeView注解的initConstructors属性进行处理
     */
    class InitConstructorArrayAdapter extends AnnotationVisitor {

        private String mConstructorDescriptor;
        private int mAttrsIndex;
        private int mDefStyleAttrIndex;

        public InitConstructorArrayAdapter(AnnotationVisitor av) {
            super(VisitorVersion.VERSION, av);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String name, String desc) {
            AnnotationVisitor annotationVisitor = super.visitAnnotation(name, desc);
            return annotationVisitor == null ? null : new InitConstructorAdapter(annotationVisitor);
        }

        /**
         * 对@InitConstructor注解进行处理
         */
        class InitConstructorAdapter extends AnnotationVisitor {

            public InitConstructorAdapter(AnnotationVisitor av) {
                super(VisitorVersion.VERSION, av);
            }

            @Override
            public void visit(String name, Object value) {
                super.visit(name, value);
                switch (name) {
                    case "constructorDescriptor":
                        mConstructorDescriptor = (String) value;
                        break;
                    case "attrsIndex":
                        mAttrsIndex = (int) value;
                        break;
                    case "defStyleAttrIndex":
                        mDefStyleAttrIndex = (int) value;
                        break;
                }
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
                mInitConstructors.put(mConstructorDescriptor, new AttrsAndDefStyleAttrIndex(mAttrsIndex, mDefStyleAttrIndex));
            }
        }
    }

    /**
     * 对@SkinChangeView的helperClassToMethodName属性进行处理
     */
    class HelperClassToMethodArrayAdapter extends AnnotationVisitor {

        public HelperClassToMethodArrayAdapter(AnnotationVisitor av) {
            super(VisitorVersion.VERSION, av);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String name, String desc) {
            AnnotationVisitor annotationVisitor = super.visitAnnotation(name, desc);
            return annotationVisitor == null ? null : new HelperClassToMethodAnnotationAdapter(annotationVisitor);
        }

        /**
         * 对@HelperClassToMethod注解进行处理
         */
        class HelperClassToMethodAnnotationAdapter extends AnnotationVisitor {

            private Type mHelperType;
            private int[] mParameterIndexes;
            private String mMethodNameAndDescriptor;

            public HelperClassToMethodAnnotationAdapter(AnnotationVisitor av) {
                super(VisitorVersion.VERSION, av);
            }

            @Override
            public void visit(String name, Object value) {
                super.visit(name, value);
                switch (name) {
                    case "helperClass":
                        mHelperType = (Type) value;
                        mHelperTypes.add(mHelperType);
                        break;
                    case "parameterIndexes":
                        mParameterIndexes = (int[]) value;
                        break;
                }
            }

            @Override
            public AnnotationVisitor visitArray(String name) {
                AnnotationVisitor annotationVisitor = super.visitArray(name);
                return annotationVisitor == null ? null : new MethodNameAndDescriptorVisitor(annotationVisitor);
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
                mMethodsNeedHandle.put(mMethodNameAndDescriptor, new HelperClassAndParameterIndexes(mHelperType, mParameterIndexes));
            }

            /**
             * 对@HelperClassToMethod注解的methodNameAndDescriptor属性进行处理
             */
            class MethodNameAndDescriptorVisitor extends AnnotationVisitor {

                private String mMethodName;
                private String mDescriptor;

                public MethodNameAndDescriptorVisitor(AnnotationVisitor av) {
                    super(VisitorVersion.VERSION, av);
                }

                @Override
                public void visit(String name, Object value) {
                    super.visit(name, value);
                    if (mMethodName == null) {
                        mMethodName = (String) value;
                    } else {
                        mDescriptor = (String) value;
                    }
                }

                @Override
                public void visitEnd() {
                    super.visitEnd();
                    mMethodNameAndDescriptor = mMethodName + mDescriptor;
                }
            }
        }

    }

    public static class HelperClassAndParameterIndexes {
        private Type mHelperClassType;
        private int[] mParameterIndexes;

        public HelperClassAndParameterIndexes(Type helperClassType, int[] parameterIndexes) {
            this.mHelperClassType = helperClassType;
            this.mParameterIndexes = parameterIndexes;
        }

        public Type getHelperClassType() {
            return mHelperClassType;
        }

        public int[] getParameterIndexes() {
            return mParameterIndexes;
        }
    }

    public static class AttrsAndDefStyleAttrIndex {

        private int mAttrsIndex;
        private int mDefStyleAttrIndex;

        public AttrsAndDefStyleAttrIndex(int attrsIndex, int defStyleAttrIndex) {
            this.mAttrsIndex = attrsIndex;
            this.mDefStyleAttrIndex = defStyleAttrIndex;
        }

        public int getAttrsIndex() {
            return mAttrsIndex;
        }

        public int getDefStyleAttrIndex() {
            return mDefStyleAttrIndex;
        }
    }
}
