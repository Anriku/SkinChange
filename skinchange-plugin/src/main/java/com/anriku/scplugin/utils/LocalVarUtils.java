package com.anriku.scplugin.utils;

import org.objectweb.asm.Type;

/**
 * Created by anriku on 2019-10-14.
 */
public class LocalVarUtils {

    /**
     * 获取一个方法最少的本地变量表的大小
     *
     * @param staticMethod 是否是静态方法
     * @param methodDesc   方法描述符
     * @return 本地变量表的最小的大小
     */
    public static int getLocalVarMin(boolean staticMethod, String methodDesc) {
        int result = 0;
        if (!staticMethod) {
            result++;
        }
        Type[] types = Type.getArgumentTypes(methodDesc);
        for (Type type : types) {
            if (type.getDescriptor().equals("J") || type.getDescriptor().equals("D")) {
                result += 2;
            } else {
                result++;
            }
        }
        return result;
    }

    /**
     * 返回所有方法参数的局部变量的位置。
     *
     * @param staticMethod 是否是静态方法
     * @param methodDesc   方法描述符
     * @return 各个方法参数的局部变量的位置。
     */
    public static int[] getAllParametersIndex(boolean staticMethod, String methodDesc) {
        Type[] types = Type.getArgumentTypes(methodDesc);
        int[] allLocalVarsIndex = new int[types.length];
        int nowIndex = 0;

        if (!staticMethod) {
            nowIndex++;
        }

        for (int i = 0; i < types.length; i++) {
            allLocalVarsIndex[i] = nowIndex;

            Type type = types[i];
            if (type.getDescriptor().equals("J") || type.getDescriptor().equals("D")) {
                nowIndex += 2;
            } else {
                nowIndex++;
            }
        }
        return allLocalVarsIndex;
    }

}
