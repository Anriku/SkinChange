package com.anriku.scplugin.visitor.skinchangeviewannotation;

import com.anriku.scplugin.utils.Constants;
import com.anriku.scplugin.visitor.VisitorVersion;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 对@SkinChangeView的helperClassToMethodName属性进行处理
 * <p>
 * Created by anriku on 2019-10-24.
 */
public class HelperClassToMethodArrayAnnotationAdapter extends AnnotationVisitor {

    private Set<Type> mHelperTypes = new HashSet<>();
    private Map<MethodInfo, HelperClassAndParameterIndexes> mMethodsNeedHandle = new HashMap<>();

    public HelperClassToMethodArrayAnnotationAdapter(AnnotationVisitor av) {
        super(VisitorVersion.VERSION, av);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(name, desc);
        if (desc.equals(Constants.HELPER_CLASS_TO_METHOD)) {
            return annotationVisitor == null ? null : new HelperClassToMethodAnnotationAdapter(annotationVisitor);
        }
        return annotationVisitor;
    }

    /**
     * 对@HelperClassToMethod注解进行处理
     */
    class HelperClassToMethodAnnotationAdapter extends AnnotationVisitor {

        private Type mHelperType;
        private int[] mParameterIndexes;
        private MethodInfoAnnotationAdapter mMethodInfoAnnotationAdapter;


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
        public AnnotationVisitor visitAnnotation(String name, String desc) {
            AnnotationVisitor annotationVisitor = super.visitAnnotation(name, desc);
            if (desc.equals(Constants.METHOD)) {
                return annotationVisitor == null ? null : (mMethodInfoAnnotationAdapter = new MethodInfoAnnotationAdapter(annotationVisitor));
            }
            return annotationVisitor;
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
            mMethodsNeedHandle.put(mMethodInfoAnnotationAdapter.getMethodInfo(), new HelperClassAndParameterIndexes(mHelperType, mParameterIndexes));
        }
    }

    public Set<Type> getHelperTypes() {
        return mHelperTypes;
    }

    public Map<MethodInfo, HelperClassAndParameterIndexes> getMethodsNeedHandle() {
        return mMethodsNeedHandle;
    }
}
