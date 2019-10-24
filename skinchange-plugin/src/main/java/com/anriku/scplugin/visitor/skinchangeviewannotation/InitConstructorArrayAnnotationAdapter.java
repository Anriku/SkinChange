package com.anriku.scplugin.visitor.skinchangeviewannotation;

import com.anriku.scplugin.utils.Constants;
import com.anriku.scplugin.visitor.VisitorVersion;

import org.objectweb.asm.AnnotationVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * 对@SkinChangeView注解的initConstructors属性进行处理
 * <p>
 * Created by anriku on 2019-10-24.
 */
class InitConstructorArrayAnnotationAdapter extends AnnotationVisitor {

    private Map<MethodInfo, AttrDefAttrDefResIndex> mInitConstructors = new HashMap<>();

    public InitConstructorArrayAnnotationAdapter(AnnotationVisitor av) {
        super(VisitorVersion.VERSION, av);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(name, desc);
        if (desc.equals(Constants.INIT_CONSTRUCTOR)) {
            return annotationVisitor == null ? null : new InitConstructorAdapter(annotationVisitor);
        }
        return annotationVisitor;
    }

    /**
     * 对@InitConstructor注解进行处理
     */
    class InitConstructorAdapter extends AnnotationVisitor {

        private MethodInfoAnnotationAdapter mMethodInfoAnnotationAdapter;
        private int mAttrsIndex;
        private int mDefStyleAttrIndex = AttrDefAttrDefResIndex.INVALID_INDEX;
        private int mDefStyleResIndex = AttrDefAttrDefResIndex.INVALID_INDEX;

        public InitConstructorAdapter(AnnotationVisitor av) {
            super(VisitorVersion.VERSION, av);
        }

        @Override
        public void visit(String name, Object value) {
            super.visit(name, value);
            switch (name) {
                case "attrsIndex":
                    mAttrsIndex = (int) value;
                    break;
                case "defStyleAttrIndex":
                    mDefStyleAttrIndex = (int) value;
                    break;
                case "defStyleResIndex":
                    mDefStyleResIndex = (int) value;
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
            mInitConstructors.put(mMethodInfoAnnotationAdapter.getMethodInfo(), new AttrDefAttrDefResIndex(mAttrsIndex, mDefStyleAttrIndex, mDefStyleResIndex));
        }
    }

    public Map<MethodInfo, AttrDefAttrDefResIndex> getInitConstructors() {
        return mInitConstructors;
    }
}