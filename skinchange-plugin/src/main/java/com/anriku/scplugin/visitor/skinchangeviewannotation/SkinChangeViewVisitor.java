package com.anriku.scplugin.visitor.skinchangeviewannotation;


import com.anriku.scplugin.utils.AddHelpersUtils;
import com.anriku.scplugin.utils.Constants;
import com.anriku.scplugin.visitor.VisitorVersion;

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
    private Map<MethodInfo, HelperClassAndParameterIndexes> mMethodsNeedHandle = new HashMap<>();
    private Map<MethodInfo, AttrDefAttrDefResIndex> mInitConstructors = new HashMap<>();

    private Map<MethodInfo, HelperClassAndParameterIndexes> mCopyMethodsNeedHandle = new HashMap<>();
    private Map<MethodInfo, AttrDefAttrDefResIndex> mCopyInitConstructors = new HashMap<>();

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
        if (desc.equals(Constants.SKIN_CHANGE_VIEW_DESCRIPTOR)) {
            return annotationVisitor == null ? null : new SkinChangeViewAnnotationAdapter(annotationVisitor);
        }
        return annotationVisitor;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        MethodInfo methodInfo = new MethodInfo(access, name, desc, signature, exceptions);

        if (mMethodsNeedHandle.isEmpty()) {
            return methodVisitor;
        }

        if (mFirstMethod) {
            AddHelpersUtils.addHelperFields(mHelperTypes, this);
            mCopyInitConstructors = new HashMap<>(mInitConstructors);
            mCopyMethodsNeedHandle = new HashMap<>(mMethodsNeedHandle);
            mFirstMethod = false;
        }

        if (name.equals("<init>") && mInitConstructors.get(methodInfo) != null && !mHelperTypes.isEmpty()) {
            mCopyInitConstructors.remove(methodInfo);
            return methodVisitor == null ? null : new AddHelpersUtils.InitHelperMethodAdapter
                    (methodVisitor, mClassName, mHelperTypes, mInitConstructors.get(methodInfo));
        } else if (name.equals("applySkinChange") && !mHelperTypes.isEmpty()) {
            return methodVisitor == null ? null : new AddHelpersUtils.ApplySkinChangeMethodAdapter(methodVisitor, mClassName, mHelperTypes);
        } else if (mMethodsNeedHandle.get(methodInfo) != null) {
            mCopyMethodsNeedHandle.remove(methodInfo);
            return methodVisitor == null ? null : new AddHelpersUtils.ResourceReplaceMethodAdapter(methodVisitor, (access & Opcodes.ACC_STATIC) != 0,
                    mClassName, mMethodsNeedHandle.get(methodInfo), desc);
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

        private HelperClassToMethodArrayAnnotationAdapter mHelperClassToMethodArrayAnnotationAdapter;
        private InitConstructorArrayAnnotationAdapter mInitConstructorArrayAnnotationAdapter;

        public SkinChangeViewAnnotationAdapter(AnnotationVisitor av) {
            super(VisitorVersion.VERSION, av);
        }

        @Override
        public AnnotationVisitor visitArray(String name) {
            AnnotationVisitor annotationVisitor = super.visitArray(name);
            switch (name) {
                case HELPER_CLASS_TO_METHOD_NAME:
                    return annotationVisitor == null ? null :
                            (mHelperClassToMethodArrayAnnotationAdapter = new HelperClassToMethodArrayAnnotationAdapter(annotationVisitor));
                case INIT_CONSTRUCTORS:
                    return annotationVisitor == null ? null :
                            (mInitConstructorArrayAnnotationAdapter = new InitConstructorArrayAnnotationAdapter(annotationVisitor));

            }
            return annotationVisitor;
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
            mMethodsNeedHandle = mHelperClassToMethodArrayAnnotationAdapter.getMethodsNeedHandle();
            mHelperTypes = mHelperClassToMethodArrayAnnotationAdapter.getHelperTypes();
            mInitConstructors = mInitConstructorArrayAnnotationAdapter.getInitConstructors();
        }
    }

}
