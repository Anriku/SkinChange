package com.anriku.scplugin.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.HashMap;


/**
 * 对R文件进行资源id以及资源名的映射记录
 * <p>
 * Created by anriku on 2019-09-14.
 */
public class RModifyVisitor extends ClassVisitor {

    private HashMap<String, Integer> mStringToInteger;

    public static byte[] getHandleBytes(byte[] originBytes, HashMap<String, Integer> stringToInteger) {
        ClassReader reader = new ClassReader(originBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new RModifyVisitor(writer, stringToInteger);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    public RModifyVisitor(ClassVisitor cv, HashMap<String, Integer> stringToInteger) {
        super(VisitorVersion.VERSION, cv);
        this.mStringToInteger = stringToInteger;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (desc.equals(Type.getDescriptor(int.class))) {
            mStringToInteger.put(name, (Integer) value);
        }
        return super.visitField(access, name, desc, signature, value);
    }

}
