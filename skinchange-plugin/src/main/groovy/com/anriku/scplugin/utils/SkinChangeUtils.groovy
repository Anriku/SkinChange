package com.anriku.scplugin.utils

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.utils.FileUtils
import com.anriku.scplugin.dump.CreateViewUtilsDump
import com.anriku.scplugin.dump.RMapsDump
import com.anriku.scplugin.dump.SCAppCompatAutoCompleteTextViewDump
import com.anriku.scplugin.dump.SCAppCompatButtonDump
import com.anriku.scplugin.dump.SCAppCompatCheckBoxDump
import com.anriku.scplugin.dump.SCAppCompatCheckedTextViewDump
import com.anriku.scplugin.dump.SCAppCompatEditTextDump
import com.anriku.scplugin.dump.SCAppCompatImageButtonDump
import com.anriku.scplugin.dump.SCAppCompatImageViewDump
import com.anriku.scplugin.dump.SCAppCompatRadioButtonDump
import com.anriku.scplugin.dump.SCAppCompatRatingBarDump
import com.anriku.scplugin.dump.SCAppCompatSeekBarDump
import com.anriku.scplugin.dump.SCAppCompatSpinnerDump
import com.anriku.scplugin.dump.SCAppCompatTextViewDump
import com.anriku.scplugin.dump.SCAppCompatToggleButtonDump
import com.anriku.scplugin.dump.SCCompatMultiAutoCompleteTextViewDump
import com.anriku.scplugin.extension.SkinChangeExtension
import com.anriku.scplugin.visitor.ActivityVisitor
import com.anriku.scplugin.visitor.AppCompatViewInflaterVisitor
import com.anriku.scplugin.visitor.ReplaceNewViewVisitor
import com.anriku.scplugin.visitor.skinchangeviewannotation.SkinChangeViewVisitor
import com.anriku.scplugin.visitor.RModifyVisitor
import com.anriku.scplugin.visitor.ResUtilsVisitor

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.compress.utils.IOUtils

import java.lang.reflect.InvocationTargetException
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

class SkinChangeUtils {

    public static SkinChangeExtension sSkinChangeExtension

    private HashMap<String, HashMap<String, Integer>> mStringToIntegers = new HashMap<>()

    /**
     * 处理文件目录下的class文件
     */
    void handleDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        // 是否是目录
        if (directoryInput.file.isDirectory()) {
            // 列出目录所有文件（包含子文件夹，子文件夹内文件）
            directoryInput.file.eachFileRecurse { File file ->
                def name = file.name
                if (isClassFile(name)) {
                    println '----------- handleDirectoryInput: <' + name + '> -----------'
                    if (isRInnerClassFile(name)) {
                        // 对R文件中的每个内部类文件的资源进行记录
                        dumpFile(RModifyVisitor.getHandleBytes(file.bytes, getStringToIntegerHashMap(name)),
                                file.parentFile.absolutePath + File.separator + name)
                    } else {
                        dumpFile(ReplaceNewViewVisitor.getHandleBytes(ActivityVisitor.getHandleBytes(SkinChangeViewVisitor.getHandleBytes(file.bytes))),
                                file.parentFile.absolutePath + File.separator + name)
                    }
                }
            }
        }
        // 处理完输入文件之后，要把输出给下一个任务
        def dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    /**
     * 处理Jar中的class文件
     */
    void handleJarInputs(JarInput jarInput, TransformOutputProvider outputProvider) {
        if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
            // 重名名输出文件,因为可能同名,会覆盖
            def jarName = jarInput.name
            def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }
            def jarFile = new JarFile(jarInput.file)
            def enumeration = jarFile.entries()
            def tmpFile = new File(jarInput.file.getParent() + File.separator + "classes_temp.jar")
            // 避免上次的缓存被重复插入
            if (tmpFile.exists()) {
                tmpFile.delete()
            }
            def jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile))
            // 对Jar文件中的每一个文件进行处理
            while (enumeration.hasMoreElements()) {
                def jarEntry = (JarEntry) enumeration.nextElement()
                def entryName = jarEntry.getName()
                def newJarEntry = new JarEntry(entryName)
                def inputStream = jarFile.getInputStream(jarEntry)

                if (isClassFile(entryName)) {
                    println '----------- handleJarInputs: <' + entryName + '> -----------'
                    if (isResUtilsClass(entryName)) {
                        jarOutputStream.putNextEntry(newJarEntry)
                        jarOutputStream.write(ResUtilsVisitor.getHandleBytes(IOUtils.toByteArray(inputStream)))
                    } else if (isAppCompatViewInflaterClassFile(entryName)) {
                        jarOutputStream.putNextEntry(newJarEntry)
                        jarOutputStream.write(AppCompatViewInflaterVisitor.getHandleBytes(IOUtils.toByteArray(inputStream)))
                    } else {
                        jarOutputStream.putNextEntry(newJarEntry)
                        jarOutputStream.write(ReplaceNewViewVisitor.getHandleBytes(ActivityVisitor.getHandleBytes(
                                SkinChangeViewVisitor.getHandleBytes(IOUtils.toByteArray(inputStream)))))
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
    void dumpFile(byte[] bytes, String outputPath) {
        FileOutputStream fos = new FileOutputStream(outputPath)
        fos.write(bytes)
        fos.close()
    }

    /**
     * 检查是否是class文件
     */
    boolean isClassFile(String name) {
        //只处理需要的class文件
        return (name.endsWith(".class"))
    }

    /**
     * 判断是否是R文件的内部类
     */
    boolean isRInnerClassFile(String name) {
        return (name.startsWith("R\$"))
    }

    /**
     * 判断是否是AppCompatViewInflater类
     */
    boolean isAppCompatViewInflaterClassFile(String name) {
        if (name == null) {
            return false
        }
        return name == PackageNameUtils.sViewPackageName + File.separator + "AppCompatViewInflater.class"
    }

    /**
     * 判断是否是ResUtils.class类
     */
    boolean isResUtilsClass(String name) {
        if (name == Constants.RES_UTILS) {
            return true
        }
        return false
    }

    private HashMap<String, Integer> getStringToIntegerHashMap(String name) {
        int index = name.indexOf("\$")
        def simpleClassName = RMapsDump.RMAPS + name.substring(index)
        simpleClassName = simpleClassName.replace("\$", "_")
        if (mStringToIntegers.get(simpleClassName) == null) {
            mStringToIntegers.put(simpleClassName, new HashMap<String, Integer>())
        }
        return mStringToIntegers.get(simpleClassName)
    }

    /**
     * 为不同的资源生成对应的RMaps类
     *
     * @param directoryInput
     * @param outputProvider
     */
    void dumpRMapsFile(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        def rmapsPath = directoryInput.file.getAbsolutePath() + File.separator + RMapsDump.RMAPS_PACKAGE.replace(".", File.separator)
        def rmapsFile = new File(rmapsPath)
        if (!rmapsFile.exists()) {
            rmapsFile.mkdirs()
        }

        mStringToIntegers.each { stringToInteger ->
            dumpFile(RMapsDump.dump(RMapsDump.RMAPS_PACKAGE,
                    stringToInteger.key.replace(".class", ""), stringToInteger.value),
                    rmapsPath + File.separator + stringToInteger.key)
        }
        def dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes,
                Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

//    /**
//     * 生成所有的AppCompat相关的View的继承类
//     */
//    void dumpAppCompatFiles(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
//        def appCompatFilesPath = directoryInput.file.getAbsolutePath() + File.separator + PackageNameUtils.sViewPackageName.replace(".", File.separator)
//        def appCompatFilesFile = new File(appCompatFilesPath)
//        if (!appCompatFilesFile.exists()) {
//            appCompatFilesFile.mkdirs()
//        }
//
//        def appCompatClasses = new ArrayList<>()
//        appCompatClasses.add(SCAppCompatAutoCompleteTextViewDump.class)
//        appCompatClasses.add(SCAppCompatButtonDump.class)
//        appCompatClasses.add(SCAppCompatCheckBoxDump.class)
//        appCompatClasses.add(SCAppCompatCheckedTextViewDump.class)
//        appCompatClasses.add(SCAppCompatEditTextDump.class)
//        appCompatClasses.add(SCAppCompatImageButtonDump.class)
//        appCompatClasses.add(SCAppCompatImageViewDump.class)
//        appCompatClasses.add(SCAppCompatRadioButtonDump.class)
//        appCompatClasses.add(SCAppCompatRatingBarDump.class)
//        appCompatClasses.add(SCAppCompatSeekBarDump.class)
//        appCompatClasses.add(SCAppCompatSpinnerDump.class)
//        appCompatClasses.add(SCAppCompatTextViewDump.class)
//        appCompatClasses.add(SCAppCompatToggleButtonDump.class)
//        appCompatClasses.add(SCCompatMultiAutoCompleteTextViewDump.class)
//        appCompatClasses.add(CreateViewUtilsDump.class)
//
//        int size = appCompatClasses.size()
//        for (int i = 0; i < size; i++) {
//            def appCompatClass = appCompatClasses.get(i)
//            try {
//                def method = appCompatClass.getMethod("dump")
//                if (method != null) {
//                    byte[] bytes = method.invoke(null)
//                    dumpFile(bytes, appCompatFilesPath + File.separator + appCompatClass.getSimpleName() + ".class")
//                }
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace()
//            } catch (IllegalAccessException e) {
//                e.printStackTrace()
//            } catch (InvocationTargetException e) {
//                e.printStackTrace()
//            }
//        }
//
//        def dest = outputProvider.getContentLocation(directoryInput.name,
//                directoryInput.contentTypes, directoryInput.scopes,
//                Format.DIRECTORY)
//        FileUtils.copyDirectory(directoryInput.file, dest)
//    }

}