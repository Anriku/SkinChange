package com.anriku.scplugin.plugin

import com.android.build.gradle.AppExtension
import com.anriku.scplugin.extension.SkinChangeExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class SkinChangePlugin implements Plugin<Project> {

    static final String EXTENSION_NAME = "skinChangeExtension"

    @Override
    void apply(Project project) {
        // 添加扩展对象
        project.extensions.create(EXTENSION_NAME, SkinChangeExtension)
        SkinChangeExtension skinChangeExtension = project.extensions.findByName(EXTENSION_NAME)

        if (!project.plugins.hasPlugin('com.android.application')) {
            return
        }
        AppExtension android = project.extensions.getByType(AppExtension)
        android.registerTransform(new com.anriku.scplugin.transform.CustomTransform(skinChangeExtension))
    }
}

