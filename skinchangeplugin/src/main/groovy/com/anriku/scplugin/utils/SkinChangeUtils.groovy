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
import com.anriku.scplugin.visitor.AppCompatViewInflaterVisitor
import com.anriku.scplugin.visitor.NewViewVisitor
import com.anriku.scplugin.visitor.RModifyVisitor
import com.anriku.scplugin.visitor.ResUtilsVisitor
import com.anriku.scplugin.visitor.SkinChangeVisitor
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.compress.utils.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
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
                        ClassVisitor cv = new SkinChangeVisitor(classWriter)
                        classReader.accept(cv, 0)
                        dumpFile(classWriter.toByteArray(), file.parentFile.absolutePath + File.separator + name)

                    } else if (checkRInnerClassFile(name)) {
                        // 对R文件中的每个内部类文件的资源进行记录
                        ClassReader classReader = new ClassReader(file.bytes)
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor cv = new RModifyVisitor(classWriter, getStringToIntegerHashMap(name))
                        classReader.accept(cv, 0)
                        dumpFile(classWriter.toByteArray(), file.parentFile.absolutePath + File.separator + name)
                    } else {
                        ClassReader classReader = new ClassReader(file.bytes)
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor cv = new NewViewVisitor(classWriter)
                        classReader.accept(cv, 0)
                        dumpFile(classWriter.toByteArray(), file.parentFile.absolutePath + File.separator + name)
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
    void handleJarInputs(JarInput jarInput, TransformOutputProvider outputProvider) {
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
                        ClassVisitor cv = new SkinChangeVisitor(classWriter)
                        classReader.accept(cv, 0)
                        byte[] code = classWriter.toByteArray()
                        jarOutputStream.write(code)
                    } else if (checkResUtilsClass(entryName)) {
                        jarOutputStream.putNextEntry(newJarEntry)
                        ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor cv = new ResUtilsVisitor(classWriter)
                        classReader.accept(cv, 0)
                        byte[] code = classWriter.toByteArray()
                        jarOutputStream.write(code)
                    } else if (checkAppCompatViewInflaterClassFile(entryName)) {
                        jarOutputStream.putNextEntry(newJarEntry)
                        ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor cv = new AppCompatViewInflaterVisitor(classWriter)
                        classReader.accept(cv, 0)
                        byte[] code = classWriter.toByteArray()
                        jarOutputStream.write(code)
                    } else if (checkIsProjectsFile(jarInput.file)) {
                        jarOutputStream.putNextEntry(newJarEntry)
                        ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        ClassVisitor cv = new NewViewVisitor(classWriter)
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
    void dumpFile(byte[] bytes, String outputPath) {
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
    boolean checkClassFile(String name) {
        //只处理需要的class文件
        return (name.endsWith(".class"))
    }

    /**
     * 检查是否是需要插桩的View文件
     *
     * @param name
     * @return
     */
    boolean checkViewClassFile(String name) {
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
    boolean checkRInnerClassFile(String name) {
        return (name.startsWith("R\$") && "R.class" != name)
    }

    boolean checkAppCompatViewInflaterClassFile(String name) {
        if (name == null) {
            return false
        }
        return name.contains("AppCompatViewInflater")
    }

    /**
     * 判断该R文件是否是项目模块的R文件
     */
    boolean checkIsProjectsFile(File file) {
        String[] projects = sSkinChangeExtension.projects
        if (projects == null) {
            return false
        }
        String path = file.getAbsolutePath()
        projects.each { project ->
            if (path.contains(project)) {
                return true
            }
        }
        return false
    }

    boolean checkResUtilsClass(String name) {
        if (name == "com/anriku/sclib/utils/ResUtils.class") {
            return true
        }
        return false
    }

    private HashMap<String, Integer> getStringToIntegerHashMap(String name) {
        int index = name.indexOf("\$")
        String simpleClassName = RMapsDump.RMAPS + name.substring(index)
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
        String rmapsPath = directoryInput.file.getAbsolutePath() + File.separator + RMapsDump.RMAPS_PACKAGE.replace(".", File.separator)
        File rmapsFile = new File(rmapsPath)
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

    void dumpAppCompatFiles(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        String appCompatFilesPath = directoryInput.file.getAbsolutePath() + File.separator + PackageNameUtils.sViewPackageName.replace(".", File.separator)
        File appCompatFilesFile = new File(appCompatFilesPath)
        if (!appCompatFilesFile.exists()) {
            appCompatFilesFile.mkdirs()
        }

        List<Class> appCompatClasses = new ArrayList<>()
        appCompatClasses.add(SCAppCompatAutoCompleteTextViewDump.class)
        appCompatClasses.add(SCAppCompatButtonDump.class)
        appCompatClasses.add(SCAppCompatCheckBoxDump.class)
        appCompatClasses.add(SCAppCompatCheckedTextViewDump.class)
        appCompatClasses.add(SCAppCompatEditTextDump.class)
        appCompatClasses.add(SCAppCompatImageButtonDump.class)
        appCompatClasses.add(SCAppCompatImageViewDump.class)
        appCompatClasses.add(SCAppCompatRadioButtonDump.class)
        appCompatClasses.add(SCAppCompatRatingBarDump.class)
        appCompatClasses.add(SCAppCompatSeekBarDump.class)
        appCompatClasses.add(SCAppCompatSpinnerDump.class)
        appCompatClasses.add(SCAppCompatTextViewDump.class)
        appCompatClasses.add(SCAppCompatToggleButtonDump.class)
        appCompatClasses.add(SCCompatMultiAutoCompleteTextViewDump.class)
        appCompatClasses.add(CreateViewUtilsDump.class)

        int size = appCompatClasses.size()
        for (int i = 0; i < size; i++) {
            Class appCompatClass = appCompatClasses.get(i)
            try {
                Method method = appCompatClass.getMethod("dump")
                if (method != null) {
                    byte[] bytes = method.invoke(null)
                    dumpFile(bytes, appCompatFilesPath + File.separator + appCompatClass.getSimpleName() + ".class")
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace()
            } catch (IllegalAccessException e) {
                e.printStackTrace()
            } catch (InvocationTargetException e) {
                e.printStackTrace()
            }
        }

        def dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes,
                Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

}