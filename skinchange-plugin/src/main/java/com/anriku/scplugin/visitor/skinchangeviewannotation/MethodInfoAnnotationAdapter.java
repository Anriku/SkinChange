package com.anriku.scplugin.visitor.skinchangeviewannotation;

import com.anriku.scplugin.visitor.VisitorVersion;

import org.objectweb.asm.AnnotationVisitor;

/**
 * Created by anriku on 2019-10-24.
 */

public class MethodInfoAnnotationAdapter extends AnnotationVisitor {

    private MethodInfo mMethodInfo;

    public MethodInfoAnnotationAdapter(AnnotationVisitor av) {
        super(VisitorVersion.VERSION, av);
        mMethodInfo = new MethodInfo();
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
        switch (name) {
            case "access":
                mMethodInfo.setAccess((Integer) value);
                break;
            case "name":
                mMethodInfo.setName((String) value);
                break;
            case "desc":
                mMethodInfo.setDesc((String) value);
                break;
            case "signature":
                mMethodInfo.setSignature((String) value);
                break;
            case "exceptions":
                mMethodInfo.setExceptions((String[]) value);
                break;
        }
    }

    public MethodInfo getMethodInfo() {
        return mMethodInfo;
    }
}
