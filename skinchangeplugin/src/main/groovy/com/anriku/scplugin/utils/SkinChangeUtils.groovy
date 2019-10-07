package com.anriku.scplugin.utils

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.utils.FileUtils
import com.anriku.scplugin.dump.ResUtilsDump
import com.anriku.scplugin.extension.SkinChangeExtension
import com.anriku.scplugin.visitor.RModifyVisitor
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.compress.utils.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

class SkinChangeUtils {

    public static SkinChangeExtension sSkinChangeExtension
    private static boolean sResUtilsGenerated

    /**
     * 处理文件目录下的class文件
     */
    static void handleDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        //是否是目录
        if (directoryInput.file.isDirectory()) {
            //列出目录所有文件（包含子文件夹，子文件夹内文件）
            directoryInput.file.eachFileRecurse { File file ->
                def name = file.name
                if (checkClassFile(name)) {
                    println '----------- handleDirectoryInput: <' + name + '> -----------'
                    if (checkViewClassFile(name)) {

                        ClassReader classReader = new ClassReader(file.bytes)
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor cv = new com.anriku.scplugin.visitor.SkinChangeVisitor(classWriter, sSkinChangeExtension.packageName)
                        classReader.accept(cv, 0)
                        dumpFile(classWriter.toByteArray(), file.parentFile.absolutePath + File.separator + name)

                    } else if (checkRInnerClassFile(name)) {
                        if (file.getAbsolutePath().contains(sSkinChangeExtension.packageName.replace(".", "/"))) {
                            generateResUtils(file.parentFile.absolutePath)

                            // 对R文件中的每个内部类文件的资源进行记录
                            ClassReader classReader = new ClassReader(file.bytes)
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            ClassVisitor cv = new RModifyVisitor(classWriter)
                            classReader.accept(cv, 0)
                            dumpFile(classWriter.toByteArray(), file.parentFile.absolutePath + File.separator + name)

                            // 对R文件中的每个内部类生成对应的RMaps_内部类名的工具类。
                            int index = name.indexOf("\$")
                            String simpleClassName = com.anriku.scplugin.dump.RMapsDump.RMAPS + name.substring(index)
                            simpleClassName = simpleClassName.replace("\$", "_")
                            dumpFile(com.anriku.scplugin.dump.RMapsDump.dump(sSkinChangeExtension.packageName, simpleClassName.replace(".class", ""), cv.stringToInteger),
                                    file.parentFile.absolutePath + File.separator + simpleClassName)
                        }
                    }
                }
            }
        }
        // 处理完输入文件之后，要把输出给下一个任务
        def dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes,
                Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    /**
     * 处理Jar中的class文件
     */
    static void handleJarInputs(JarInput jarInput, TransformOutputProvider outputProvider) {
        if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
            // 重名名输出文件,因为可能同名,会覆盖
            def jarName = jarInput.name
            def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }
            JarFile jarFile = new JarFile(jarInput.file)
            Enumeration enumeration = jarFile.entries()
            File tmpFile = new File(jarInput.file.getParent() + File.separator + "classes_temp.jar")
            // 避免上次的缓存被重复插入
            if (tmpFile.exists()) {
                tmpFile.delete()
            }
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile))
            // 对Jar文件中的每一个文件进行处理
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                JarEntry newJarEntry = new JarEntry(entryName)
                InputStream inputStream = jarFile.getInputStream(jarEntry)

                if (checkClassFile(entryName)) {
                    println '----------- handleJarInputs: <' + entryName + '> -----------'
                    if (checkViewClassFile(entryName)) {
                        jarOutputStream.putNextEntry(newJarEntry)
                        ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor cv = new com.anriku.scplugin.visitor.SkinChangeVisitor(classWriter, sSkinChangeExtension.packageName)
                        classReader.accept(cv, 0)
                        byte[] code = classWriter.toByteArray()
                        jarOutputStream.write(code)
                    } else {
                        jarOutputStream.putNextEntry(newJarEntry)
                        jarOutputStream.write(IOUtils.toByteArray(inputStream))
                    }
                } else {
                    jarOutputStream.putNextEntry(newJarEntry)
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }
                jarOutputStream.closeEntry()
            }
            //结束
            jarOutputStream.close()
            jarFile.close()
            def dest = outputProvider.getContentLocation(jarName + md5Name,
                    jarInput.contentTypes, jarInput.scopes, Format.JAR)
            FileUtils.copyFile(tmpFile, dest)
            tmpFile.delete()
        }
    }

    /**
     * 将字节流写入文件
     *
     * @param bytes
     * @param outputPath
     */
    static void dumpFile(byte[] bytes, String outputPath) {
        FileOutputStream fos = new FileOutputStream(outputPath)
        fos.write(bytes)
        fos.close()
    }

    /**
     * 检查是否是class文件
     *
     * @param name
     * @return
     */
    static boolean checkClassFile(String name) {
        //只处理需要的class文件
        return (name.endsWith(".class"))
    }

    /**
     * 检查是否是需要插桩的View文件
     *
     * @param name
     * @return
     */
    static boolean checkViewClassFile(String name) {
        return (!name.startsWith("R\$")
                && "R.class" != name && "BuildConfig.class" != name
                && "androidx/appcompat/widget/AppCompatImageView.class" == name)
    }

    /**
     * 判断是否是R文件的内部类。
     *
     * @param name
     * @return
     */
    static boolean checkRInnerClassFile(String name) {
        return (name.startsWith("R\$") && "R.class" != name)
    }

    /**
     * 生成ResUtils工具类
     *
     * @param absolutePath
     */
    static void generateResUtils(String absolutePath) {
        if (!sResUtilsGenerated) {
            println "betawenbeta:generateResUtils:" + absolutePath
            byte[] bytes = ResUtilsDump.dump(sSkinChangeExtension.packageName)
            FileOutputStream fos = new FileOutputStream(absolutePath + File.separator + ResUtilsDump.SIMPLE_NAME + ".class")
            fos.write(bytes)
            fos.close()
            sResUtilsGenerated = true
        }
    }
}