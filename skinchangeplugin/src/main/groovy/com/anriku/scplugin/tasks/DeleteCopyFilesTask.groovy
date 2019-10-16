package com.anriku.scplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class DeleteCopyFilesTask extends DefaultTask {

    @TaskAction
    public void deleteCopyFiles() {
        def file = new File(project.getRootDir().path + File.separator + "copyFiles.txt")
        if (!file.exists()) {
            return
        }
        def fr = new FileReader(file)
        def br = new BufferedReader(fr)

        try {
            def line
            while ((line = br.readLine()) != null) {
                def tmpFile = new File(line)
                if (tmpFile.exists()) {
                    tmpFile.delete()
                }
            }
            file.delete()
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            fr.close()
            br.close()
        }
    }

}