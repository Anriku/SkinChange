package com.anriku.scplugin.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 进行资源获取的插桩
 * <p>
 * Created by anriku on 2019-09-13.
 */
public class SkinChangeVisitor extends ClassVisitor {

    public SkinChangeVisitor(ClassVisitor classVisitor) {
        super(VisitorVersion.VERSION, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        return methodVisitor;
    }

}
