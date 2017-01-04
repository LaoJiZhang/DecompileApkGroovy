import brut.androlib.ApkDecoder
import brut.androlib.err.CantFindFrameworkResException
import brut.androlib.err.InFileNotFoundException
import brut.androlib.err.OutDirExistsException
import brut.directory.DirectoryException

class DecompileApk {
    public static String ORIGIN_FILE_PATH = "apk/"
    public static String OUT_FILE_PATH = "apk/outDir/"
    public static String KEY_WORD = "retrofit"

    static def decodeApk = { File file ->
//		String outName = APK_NAME + ".out_" + new Date().format("yyyyMMddHHmmss")
        String outName = file.getName() + ".out"
        ApkDecoder decoder = new ApkDecoder()
        File outDir = new File(OUT_FILE_PATH + outName)
        decoder.setOutDir(outDir)
        if (outDir.exists()) {
            outDir.deleteDir()
        }
        decoder.setApkFile(file)
        try {
            decoder.decode()
        } catch (IOException ex) {
            println("Could not modify file. Please ensure you have permission.")
            System.exit(1)
        } catch (OutDirExistsException ex) {
            println("Destination directory (" + outDir.getAbsolutePath() + ") already exists. Use -f switch if you want to overwrite it.")
            System.exit(1)
        } catch (InFileNotFoundException ex) {
            println("Input file (" + APK_NAME + ") was not found or was not readable.")
            System.exit(1)
        } catch (CantFindFrameworkResException ex) {
            println("Can't find framework resources for package of id: " + String.valueOf(ex.getPkgId()) + ". You must install proper framework files, see project website for more info.")
            System.exit(1)
        } catch (DirectoryException ex) {
            println("Could not modify internal dex files. Please ensure you have permission.")
            System.exit(1)
        }
        outDir
    }

    static def searchKeyWordFromDecodeApkFile = { File outDir ->
        StringBuilder sb = new StringBuilder()
        outDir.eachFileRecurse { file ->
            if (file.getAbsolutePath().endsWith(".smali")) {
                def contain = false
                ((File) file).eachLine { line ->
                    if (line.toLowerCase().contains(KEY_WORD.toLowerCase())) {
                        contain = true
                    }
                }
                if (contain) {
                    sb.append(file.getAbsolutePath())
                    sb.append("\n")
                }
            }
        }
        sb.toString()
    }

    static def save2File = { String dir, String name, String content ->
        if (content.length() > 0) {
            String fileName = dir + "/" + name + "_" + KEY_WORD + ".txt"
            new File(fileName).write(content)
            Process p = ("open " + fileName).execute()
            println "${p.text}"
        }
    }

    static def decodeMultiApk = {
        File apkPath = new File(ORIGIN_FILE_PATH)
        apkPath.eachFile { File apkFile ->
            if (!apkFile.isDirectory() && apkFile.getAbsolutePath().endsWith(".apk")) {
                Thread.start {
                    println(Thread.currentThread())
                    File outDirFile = decodeApk.call(apkFile)
                    String content = searchKeyWordFromDecodeApkFile.call(outDirFile)
                    save2File.call(outDirFile.getAbsolutePath(), apkFile.getName(), content)
                    println "======================Success======================"
                }
            }
        }
    }

    public static void main(String[] args) {
        decodeMultiApk()
    }
}
