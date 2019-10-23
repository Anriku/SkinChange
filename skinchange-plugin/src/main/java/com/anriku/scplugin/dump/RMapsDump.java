package com.anriku.scplugin.dump;

import com.anriku.scplugin.utils.IntLdcUtils;

import org.objectweb.asm.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by anriku on 2019-09-15.
 */
public class RMapsDump {

    public static final String RMAPS_PACKAGE = "com.anriku.rmaps";
    public static final String RMAPS = "RMaps";
    public static final String STRING_TO_INTEGER = "stringToInteger";
    public static final String INTEGER_TO_STRING = "integerToString";
    public static String sClassName;

    public static byte[] dump(String packageName, String simpleClassName, HashMap<String, Integer> stringToInteger) {
        sClassName = packageName.replace(".", "/") + File.separator + simpleClassName;

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_SUPER, sClassName, null, "java/lang/Object", null);

        cw.visitSource(simpleClassName + ".java", null);

        addMaps(cw);
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            // 实例化两个Map
            newMap(mv, sClassName, STRING_TO_INTEGER);
            newMap(mv, sClassName, INTEGER_TO_STRING);
            // 给两个Map添加元素
            {
                Set<Map.Entry<String, Integer>> entries = stringToInteger.entrySet();
                for (Map.Entry<String, Integer> entry : entries) {
                    addContentsToStringToInteger(mv, sClassName, entry.getKey(), entry.getValue());
                    addContentsToIntegerToString(mv, sClassName, entry.getValue(), entry.getKey());
                }
            }

            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(3, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    public static void addMaps(ClassVisitor cw) {
        FieldVisitor fv;
        {
            fv = cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, STRING_TO_INTEGER, "Ljava/util/HashMap;", "Ljava/util/HashMap<Ljava/lang/String;Ljava/lang/Integer;>;", null);
            fv.visitEnd();
        }
        {
            fv = cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, INTEGER_TO_STRING, "Ljava/util/HashMap;", "Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/String;>;", null);
            fv.visitEnd();
        }
    }

    public static void newMap(MethodVisitor mv, String className, String name) {
        mv.visitTypeInsn(Opcodes.NEW, "java/util/HashMap");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false);
        mv.visitFieldInsn(Opcodes.PUTSTATIC, className, name, "Ljava/util/HashMap;");
    }

    public static void addContentsToStringToInteger(MethodVisitor mv, String className, Object key, Integer value) {
        mv.visitFieldInsn(Opcodes.GETSTATIC, className, STRING_TO_INTEGER, "Ljava/util/HashMap;");
        mv.visitLdcInsn(key);

        IntLdcUtils.loadIntValue(mv, value);

        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/HashMap", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false);
        mv.visitInsn(Opcodes.POP);
    }

    public static void addContentsToIntegerToString(MethodVisitor mv, String className, Integer key, Object value) {
        mv.visitFieldInsn(Opcodes.GETSTATIC, className, INTEGER_TO_STRING, "Ljava/util/HashMap;");

        if (key >= -1 && key <= 5) {
            mv.visitInsn(key + 3);
        } else if (key >= -128 && key <= 127) {
            mv.visitIntInsn(Opcodes.BIPUSH, key);
        } else if (key >= -32768 && key <= 32767) {
            mv.visitIntInsn(Opcodes.SIPUSH, key);
        } else {
            mv.visitLdcInsn(key);
        }

        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
        mv.visitLdcInsn(value);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/HashMap", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", false);
        mv.visitInsn(Opcodes.POP);
    }
}
