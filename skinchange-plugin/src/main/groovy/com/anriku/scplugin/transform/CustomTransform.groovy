package com.anriku.scplugin.transform

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.anriku.scplugin.utils.PackageNameUtils
import com.anriku.scplugin.utils.SkinChangeUtils
import com.anriku.scplugin.extension.SkinChangeExtension

class CustomTransform extends Transform {

    SkinChangeExtension skinChangeExtension

    CustomTransform(SkinChangeExtension skinChangeExtension) {
        this.skinChangeExtension = skinChangeExtension
        SkinChangeUtils.sSkinChangeExtension = skinChangeExtension
    }

    @Override
    String getName() {
        return "SkinChangeTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        PackageNameUtils.setCompatibleType(skinChangeExtension.isUseAndroidX)
        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider

        // 删除之前的输出
        if (outputProvider != null)
            outputProvider.deleteAll()

        def skinChangeUtils = new SkinChangeUtils()
        def dInput = null
        // 遍历inputs
        inputs.each { TransformInput input ->
            // 遍历directoryInputs
            input.directoryInputs.each { DirectoryInput directoryInput ->
                dInput = directoryInput
                //处理directoryInputs
                skinChangeUtils.handleDirectoryInput(directoryInput, outputProvider)
            }

            //遍历jarInputs
            input.jarInputs.each { JarInput jarInput ->
                //处理jarInputs
                skinChangeUtils.handleJarInputs(jarInput, outputProvider)
            }
        }
        // 生成RMaps系列类
        if (dInput != null) {
            skinChangeUtils.dumpRMapsFile(dInput, outputProvider)
        }
//        // 生成一系列的AppCompat的代理类
//        if (dInput != null) {
//            skinChangeUtils.dumpAppCompatFiles(dInput, outputProvider)
//        }
    }

}