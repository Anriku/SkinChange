package com.anriku.scplugin.plugin

import com.android.build.gradle.AppExtension
import com.anriku.scplugin.extension.SkinChangeExtension
import com.anriku.scplugin.tasks.DeleteCopyFilesTask
import com.anriku.scplugin.tasks.SkinsHandleTask
import com.anriku.scplugin.transform.CustomTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class SkinChangePlugin implements Plugin<Project> {

    static final String EXTENSION_NAME = "skinChangeExtension"

    @Override
    void apply(Project project) {
        // 添加扩展对象
        project.extensions.create(EXTENSION_NAME, SkinChangeExtension)
        def skinChangeExtension = project.extensions.findByName(EXTENSION_NAME)

        // 添加皮肤资源处理任务
        project.task("skinsHandleTask", type: SkinsHandleTask) { task ->
            // 读取扩展对象的中设置的值并赋值给Task对应的约定映射
            conventionMapping.skinsPath = { skinChangeExtension.skinsPath }
        }
        // 删除生成的一些复制文件
        project.task("deleteCopyFiles", type: DeleteCopyFilesTask)

        project.afterEvaluate {
            project.tasks.getByName("preBuild").dependsOn("skinsHandleTask")
            project.tasks.getByName("clean").dependsOn("deleteCopyFiles")
            project.tasks.matching { task ->
                task.name.startsWith("assemble")
            }.each { task ->
                task.dependsOn("deleteCopyFiles")
            }
        }


        if (!project.plugins.hasPlugin('com.android.application')) {
            return
        }
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new CustomTransform(skinChangeExtension))
    }
}

