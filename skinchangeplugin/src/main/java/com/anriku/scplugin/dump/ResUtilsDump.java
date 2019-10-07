package com.anriku.scplugin.dump;

import org.objectweb.asm.*;

import java.io.File;

public class ResUtilsDump {

    public static final String SIMPLE_NAME = "ResUtils";
    public static String sClassName = "";

    public static byte[] dump(String packageName) {
        sClassName = packageName.replace(".", "/") + File.separator + SIMPLE_NAME;

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;

        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, sClassName, null, "java/lang/Object", null);

        cw.visitSource(SIMPLE_NAME + ".java", null);

        cw.visitInnerClass("android/content/SharedPreferences$Editor", "android/content/SharedPreferences", "Editor", Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE);

        {
            fv = cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "SP_NAME", "Ljava/lang/String;", null, "SkinChange");
            fv.visitEnd();
        }
        {
            fv = cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "KEY", "Ljava/lang/String;", null, "skin_name");
            fv.visitEnd();
        }
        {
            fv = cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "sSkinSuffix", "Ljava/lang/String;", null, null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(10, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "L" + sClassName + ";", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "init", "(Landroid/content/Context;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(18, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn("SkinChange");
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "android/content/Context", "getSharedPreferences", "(Ljava/lang/String;I)Landroid/content/SharedPreferences;", false);
            mv.visitVarInsn(Opcodes.ASTORE, 1);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(19, l1);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitLdcInsn("skin_name");
            mv.visitLdcInsn("");
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "android/content/SharedPreferences", "getString", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", true);
            mv.visitFieldInsn(Opcodes.PUTSTATIC, sClassName, "sSkinSuffix", "Ljava/lang/String;");
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(20, l2);
            mv.visitInsn(Opcodes.RETURN);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("context", "Landroid/content/Context;", null, l0, l3, 0);
            mv.visitLocalVariable("sharedPreferences", "Landroid/content/SharedPreferences;", null, l1, l3, 1);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "changeSkin", "(Landroid/content/Context;Ljava/lang/String;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(23, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitLdcInsn("SkinChange");
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "android/content/Context", "getSharedPreferences", "(Ljava/lang/String;I)Landroid/content/SharedPreferences;", false);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "android/content/SharedPreferences", "edit", "()Landroid/content/SharedPreferences$Editor;", true);
            mv.visitVarInsn(Opcodes.ASTORE, 2);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(24, l1);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitLdcInsn("skin_name");
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "android/content/SharedPreferences$Editor", "putString", "(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;", true);
            mv.visitInsn(Opcodes.POP);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(25, l2);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "android/content/SharedPreferences$Editor", "apply", "()V", true);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(26, l3);
            mv.visitInsn(Opcodes.RETURN);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLocalVariable("context", "Landroid/content/Context;", null, l0, l4, 0);
            mv.visitLocalVariable("skinName", "Ljava/lang/String;", null, l0, l4, 1);
            mv.visitLocalVariable("editor", "Landroid/content/SharedPreferences$Editor;", null, l1, l4, 2);
            mv.visitMaxs(3, 3);
            mv.visitEnd();
        }
        // @DrawableRes的资源替换
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "getNewDrawableResId", "(I)I", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(29, l0);
            mv.visitVarInsn(Opcodes.ILOAD, 0);
            mv.visitFieldInsn(Opcodes.GETSTATIC, packageName.replace(".", "/") + File.separator + RMapsDump.RMAPS + "_drawable", "integerToString", "Ljava/util/HashMap;");
            mv.visitFieldInsn(Opcodes.GETSTATIC, packageName.replace(".", "/") + File.separator + RMapsDump.RMAPS + "_drawable", "stringToInteger", "Ljava/util/HashMap;");
            mv.visitFieldInsn(Opcodes.GETSTATIC, sClassName, "sSkinSuffix", "Ljava/lang/String;");
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, sClassName, "getNewResId", "(ILjava/util/Map;Ljava/util/Map;Ljava/lang/String;)I", false);
            mv.visitInsn(Opcodes.IRETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("resId", "I", null, l0, l1, 0);
            mv.visitMaxs(4, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PRIVATE + Opcodes.ACC_STATIC, "getNewResId", "(ILjava/util/Map;Ljava/util/Map;Ljava/lang/String;)I", "(ILjava/util/Map<Ljava/lang/Integer;Ljava/lang/String;>;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;Ljava/lang/String;)I", null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(33, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.ILOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/String");
            mv.visitVarInsn(Opcodes.ASTORE, 4);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(34, l1);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitVarInsn(Opcodes.ALOAD, 4);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(Opcodes.ALOAD, 3);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
            mv.visitVarInsn(Opcodes.ASTORE, 5);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(35, l2);
            mv.visitVarInsn(Opcodes.ALOAD, 5);
            Label l3 = new Label();
            mv.visitJumpInsn(Opcodes.IFNONNULL, l3);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(36, l4);
            mv.visitVarInsn(Opcodes.ILOAD, 0);
            mv.visitInsn(Opcodes.IRETURN);
            mv.visitLabel(l3);
            mv.visitLineNumber(38, l3);
            mv.visitFrame(Opcodes.F_APPEND, 2, new Object[]{"java/lang/String", "java/lang/Integer"}, 0, null);
            mv.visitVarInsn(Opcodes.ALOAD, 5);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            mv.visitInsn(Opcodes.IRETURN);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLocalVariable("resId", "I", null, l0, l5, 0);
            mv.visitLocalVariable("integerToString", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/Integer;Ljava/lang/String;>;", l0, l5, 1);
            mv.visitLocalVariable("stringToInteger", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;", l0, l5, 2);
            mv.visitLocalVariable("skinSuffix", "Ljava/lang/String;", null, l0, l5, 3);
            mv.visitLocalVariable("name", "Ljava/lang/String;", null, l1, l5, 4);
            mv.visitLocalVariable("newId", "Ljava/lang/Integer;", null, l2, l5, 5);
            mv.visitMaxs(3, 6);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(15, l0);
            mv.visitLdcInsn("_night");
            mv.visitFieldInsn(Opcodes.PUTSTATIC, sClassName, "sSkinSuffix", "Ljava/lang/String;");
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
