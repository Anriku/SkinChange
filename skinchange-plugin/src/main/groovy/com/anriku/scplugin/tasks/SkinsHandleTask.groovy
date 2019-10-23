package com.anriku.scplugin.tasks

import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils
import org.dom4j.Element
import org.dom4j.io.SAXReader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction


/**
 * 此任务用于将所有的皮肤资源移到项目中
 */
public class SkinsHandleTask extends DefaultTask {

    @Input
    String[] skinsPath

    @TaskAction
    public void addSkinsResourcesToProject() {
        if (getSkinsPath() != null) {
            getSkinsPath().each { skinPath ->
                handleSkinDirectory(project.rootDir.path + File.separator + skinPath, skinPath)
            }
        }
    }

    /**
     * 处理皮肤目录
     *
     * @param directoryPath
     */
    public void handleSkinDirectory(String directoryPath, String skinPath) {
        def directory = new File(directoryPath)
        def files = directory.listFiles()
        files.each { file ->
            if (file.isDirectory()) {
                handleSkinDirectory(file.path, skinPath)
            } else {
                println "handle skin resources file <" + file + ">"
                handleSkinFile(file, skinPath)
            }
        }
    }

    /**
     * 处理皮肤资源文件
     *
     * @param filePath
     */
    public void handleSkinFile(File file, String skinPath) {
        def copyFile = copyFile(file, skinPath)
        if (copyFile.path.contains("/values/")) {
            modifyValuesFilesContent(copyFile, skinPath)
        }
    }

    /**
     * 对复制过来的values下的文件进行处理
     *
     * @param copyFile 复制到项目下的values资源文件
     */
    public void modifyValuesFilesContent(File copyFile, String skinPath) {
        def saxReader = new SAXReader()
        def document = saxReader.read(copyFile)
        def root = document.getRootElement()
        def iterator = root.elementIterator()

        while (iterator.hasNext()) {
            def element = (Element) iterator.next()
            def nameAttr = element.attribute("name")
            if (nameAttr != null) {
                def nameValue = nameAttr.getValue()
                nameValue = nameValue + "_" + DigestUtils.md5Hex(skinPath)
                nameAttr.setValue(nameValue)
            }

        }
        def fw = new FileWriter(copyFile)
        document.write(fw)
        fw.flush()
        fw.close()
    }

    /**
     * 将各个皮肤目录下的资源复制到主项目中
     *
     * @param file 原始的资源文件
     * @param skinPath 皮肤文件夹名
     * @return 复制后的资源文件
     */
    public File copyFile(File file, String skinPath) {
        def projectPath = project.getProjectDir().absolutePath
        def filePath = file.getAbsolutePath()
        def index = filePath.lastIndexOf(File.separator + "res" + File.separator)
        // 复制文件的文件名
        def copyFileName = projectPath + File.separator + "src" + File.separator + "main" + filePath.substring(index)
        // 给文件添加上对应的皮肤名的md5的16进制值
        def dotIndex = copyFileName.lastIndexOf(".")
        copyFileName = copyFileName.substring(0, dotIndex) + "_" + DigestUtils.md5Hex(skinPath) + copyFileName.substring(dotIndex)

        recordSkinFile(copyFileName)

        // 将原始文件的内容复制到复制文件中去
        File copyFile = new File(copyFileName)
        if (!copyFile.exists()) {
            FileUtils.createFile(copyFile, "")
        }
        FileUtils.copyFile(file, copyFile)
        return copyFile
    }

    /**
     * 对复制文件进行记录
     * @param filePath 复制文件的完全路径
     */
    private void recordSkinFile(String filePath) {
        def file = new File(project.getRootDir().path + File.separator + "copyFiles.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        def fw = new FileWriter(file, true)
        def bw = new BufferedWriter(fw)
        try {
            bw.write(filePath + "\n")
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            bw.close()
        }
    }
}