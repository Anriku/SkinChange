package com.anriku.scplugin.visitor.skinchangeviewannotation;

import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.Objects;


/**
 * Created by anriku on 2019-10-24.
 */

public class MethodInfo {

    private int mAccess = Opcodes.ACC_PUBLIC;
    private String mName;
    private String mDesc;
    private String mSignature;
    private String[] mExceptions;

    public MethodInfo() {
    }

    public MethodInfo(int access, String name, String desc, String signature, String[] exceptions) {
        this.mAccess = access;
        this.mName = name;
        this.mDesc = desc;
        this.mSignature = signature;
        this.mExceptions = exceptions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mAccess, mAccess, mDesc, mSignature, mExceptions);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof MethodInfo)) {
            return false;
        }
        MethodInfo other = (MethodInfo) o;
        return mAccess == other.mAccess &&
                Objects.equals(mName, other.mName) &&
                Objects.equals(mDesc, other.mDesc) &&
                Objects.equals(mSignature, other.mSignature) &&
                Arrays.equals(mExceptions, other.mExceptions);
    }

    @Override
    public String toString() {
        return "MethodInfo{" +
                "mAccess=" + mAccess +
                ", mName='" + mName + '\'' +
                ", mDesc='" + mDesc + '\'' +
                ", mSignature='" + mSignature + '\'' +
                ", mExceptions=" + Arrays.toString(mExceptions) +
                '}';
    }

    public int getAccess() {
        return mAccess;
    }

    public void setAccess(int access) {
        this.mAccess = access;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        this.mDesc = desc;
    }

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String signature) {
        this.mSignature = signature;
    }

    public String[] getExceptions() {
        return mExceptions;
    }

    public void setExceptions(String[] exceptions) {
        this.mExceptions = exceptions;
    }
}
