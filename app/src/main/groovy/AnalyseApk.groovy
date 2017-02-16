import brut.androlib.ApkDecoder

@Singleton(lazy = true, strict = false)
class AnalyseApk {

    static HashMap<String, Set<String>> sNetworkMap = new HashMap<>()
    static HashMap<String, Set<String>> sImagecLoaderMap = new HashMap<>()
    static HashMap<String, Set<String>> sJsonMap = new HashMap<>()
    static HashMap<String, Set<String>> sDataBase = new HashMap<>()

    private AnalyseApk() {
        sNetworkMap.put("Okttp", new HashSet<String>())
        sNetworkMap.put("Volley", new HashSet<String>())
        sNetworkMap.put("android-async-http", new HashSet<String>())

        sImagecLoaderMap.put("UniversalImageLoader", new HashSet<String>())
        sImagecLoaderMap.put("Fresco", new HashSet<String>())
        sImagecLoaderMap.put("Glide", new HashSet<String>())
        sImagecLoaderMap.put("Picasso", new HashSet<String>())

        sJsonMap.put("Gson", new HashSet<String>())
        sJsonMap.put("Fastjson", new HashSet<String>())
        sJsonMap.put("Jackson", new HashSet<String>())

        sDataBase.put("GreenDao", new HashSet<String>())
        sDataBase.put("OrmLite", new HashSet<String>())
    }

    def decodeApk = { File apk ->
        String outName = apk.getName() + ".out"
        ApkDecoder decoder = new ApkDecoder()
        File outDir = new File(Main.ORIGIN_FILE_PATH + outName)
        decoder.setOutDir(outDir)
        outDir?.deleteDir()
        decoder.setApkFile(apk)
        try {
            decoder.decode()
            searchKeyWordFromDecodeApkFile.call(outDir)
        } catch (Exception e) {
            Main.printlnMsg("反编译失败，删除文件及其文件夹" + e.toString())
            outDir.deleteOnExit()
//            System.exit(1)
        } finally {
            Main.successfulCount--
            Main.printlnMsg(apk.getName() + " decode Apk Success    剩余未反编译数量为：" + Main.successfulCount)
            if (Main.successfulCount == 0) {
                Main.sExecutorService.shutdown()

                String xmlContent = formatAnalyseInfo2Xml()
                new File(Main.ORIGIN_FILE_PATH + "analyseInfo.xml").write(xmlContent)

                String content = printlnAnalyseInfo.call()
                new File(Main.ORIGIN_FILE_PATH + "result.txt").write(content)
            }
        }
    }

    def searchKeyWordFromDecodeApkFile = { File outDir ->
        outDir.eachFileRecurse { File childFile ->
            if (childFile.getAbsolutePath().endsWith(".smali")) {
                childFile.eachLine { line ->
                    analyseNetwork.call(line, childFile)
                    analyseImagecLoader.call(line, childFile)
                    analyseJson.call(line, childFile)
                    analyseDataBase.call(line, childFile)
                }
            }
        }
    }

    def analyseNetwork = { String line, File childFile ->
        if (line.toLowerCase().contains("Okttp".toLowerCase())) {
            sNetworkMap.get("Okttp").add(childFile.getAbsolutePath())
        } else if (line.toLowerCase().contains("Volley".toLowerCase())) {
            sNetworkMap.get("Volley").add(childFile.getAbsolutePath())
        } else if (line.toLowerCase().contains("async".toLowerCase())) {
            sNetworkMap.get("android-async-http").add(childFile.getAbsolutePath())
        }
    }

    def analyseImagecLoader = { String line, File childFile ->
        if (line.toLowerCase().contains("ImageLoader".toLowerCase())) {
            sImagecLoaderMap.get("UniversalImageLoader").add(childFile.getAbsolutePath())
        } else if (line.toLowerCase().contains("Fresco".toLowerCase())) {
            sImagecLoaderMap.get("Fresco").add(childFile.getAbsolutePath())
        } else if (line.toLowerCase().contains("Glide".toLowerCase())) {
            sImagecLoaderMap.get("Glide").add(childFile.getAbsolutePath())
        } else if (line.toLowerCase().contains("Picasso".toLowerCase())) {
            sImagecLoaderMap.get("Picasso").add(childFile.getAbsolutePath())
        }
    }

    def analyseJson = { String line, File childFile ->
        if (line.toLowerCase().contains("Gson".toLowerCase())) {
            sJsonMap.get("Gson").add(childFile.getAbsolutePath())
        } else if (line.toLowerCase().contains("Fastjson".toLowerCase())) {
            sJsonMap.get("Fastjson").add(childFile.getAbsolutePath())
        } else if (line.toLowerCase().contains("Jackson".toLowerCase())) {
            sJsonMap.get("Jackson").add(childFile.getAbsolutePath())
        }
    }

    def analyseDataBase = { String line, File childFile ->
        if (line.toLowerCase().contains("GreenDao".toLowerCase())) {
            sNetworkMap.get("GreenDao").add(childFile.getAbsolutePath())
        } else if (line.toLowerCase().contains("OrmLite".toLowerCase())) {
            sNetworkMap.get("OrmLite").add(childFile.getAbsolutePath())
        }
    }

    def formatAnalyseInfo2Xml = {
        def fragment = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
        fragment += "<info>"
        fragment += formatSingleAnalyseInfo2Xml("Network", sNetworkMap)
        fragment += formatSingleAnalyseInfo2Xml("ImagecLoader", sImagecLoaderMap)
        fragment += formatSingleAnalyseInfo2Xml("Json", sJsonMap)
        fragment += formatSingleAnalyseInfo2Xml("DataBase", sDataBase)
        fragment += "</info>"
        return fragment
    }

    def formatSingleAnalyseInfo2Xml = { String root, HashMap map ->
        def fragment = "<${root}>"
        map.each { key, value ->
            fragment += "<key name='${key}'>"
            value.each {
                fragment += "<location>${it.toString()}</location>"
            }
            fragment += "</key>"
        }
        fragment += "</${root}>"
        return fragment
    }

    def printlnAnalyseInfo = {
        StringBuilder sb = new StringBuilder()
        sb.append("-------------------------------------------- 统计结果输入 start --------------------------------------------\n")

        sb.append("--------------------------- Network统计结果 start ---------------------------\n")
        sb.append(getPartContent.call("Okttp", sNetworkMap.get("Okttp")))
        sb.append(getPartContent.call("Volley", sNetworkMap.get("Volley")))
        sb.append(getPartContent.call("android-async-http", sNetworkMap.get("android-async-http")))
        sb.append("--------------------------- Network统计结果 end ---------------------------\n\n\n")

        sb.append("--------------------------- ImagecLoader统计结果 start ---------------------------\n")
        sb.append(getPartContent.call("UniversalImageLoader", sImagecLoaderMap.get("UniversalImageLoader")))
        sb.append(getPartContent.call("Fresco", sImagecLoaderMap.get("Fresco")))
        sb.append(getPartContent.call("Glide", sImagecLoaderMap.get("Glide")))
        sb.append(getPartContent.call("Picasso", sImagecLoaderMap.get("Picasso")))
        sb.append("--------------------------- ImagecLoader统计结果 end ---------------------------\n\n\n")

        sb.append("--------------------------- Json统计结果 start ---------------------------\n")
        sb.append(getPartContent.call("Gson", sJsonMap.get("Gson")))
        sb.append(getPartContent.call("Fastjson", sJsonMap.get("Fastjson")))
        sb.append(getPartContent.call("Jackson", sJsonMap.get("Jackson")))
        sb.append("--------------------------- Json统计结果 end ---------------------------\n\n\n")

        sb.append("--------------------------- DataBase统计结果 start ---------------------------\n")
        sb.append(getPartContent.call("GreenDao", sDataBase.get("GreenDao")))
        sb.append(getPartContent.call("OrmLite", sDataBase.get("OrmLite")))
        sb.append("--------------------------- DataBase统计结果 end ---------------------------\n\n\n")

        sb.append("-------------------------------------------- 统计结果输入 end --------------------------------------------\n")

        String content = sb.toString()
//        Main.printlnMsg(content)

//        Main.printlnMsg("---------------------------统计结果输入 start---------------------------")
//        Main.printlnMsg("Network统计结果：Okttp = " + sNetworkMap.get("Okttp").size() + "    Volley = " + sNetworkMap.get("Volley").size() + "       android-async-http = " + sNetworkMap.get("android-async-http").size())
//        Main.printlnMsg("ImagecLoader统计结果：UniversalImageLoader = " + sImagecLoaderMap.get("UniversalImageLoader").size() + "    Fresco = " + sImagecLoaderMap.get("Fresco").size() + "       Glide = " + sImagecLoaderMap.get("Glide").size() + "       Picasso = " + sImagecLoaderMap.get("Picasso").size())
//        Main.printlnMsg("Json统计结果：Gson = " + sJsonMap.get("Gson").size() + "    Fastjson = " + sJsonMap.get("Fastjson").size() + "       Jackson = " + sJsonMap.get("Jackson").size())
//        Main.printlnMsg("DataBase统计结果：GreenDao = " + sDataBase.get("GreenDao").size() + "    OrmLite = " + sDataBase.get("OrmLite").size())
//        Main.printlnMsg("---------------------------统计结果输入 end---------------------------")
        content
    }

    def getPartContent = { String key, Set<String> sets ->
        StringBuilder sb = new StringBuilder()
        sb.append("++++++++++++++++++++ 包含 " + key + " 文件路径 start ++++++++++++++++++++\n")
//        for (String item : sets.sort(false)) {
//            sb.append(item)
//            sb.append("\n")
//        }
        for (item in sets.sort(false)) {
            sb.append(item)
            sb.append("\n")
        }
        sb.append("++++++++++++++++++++ 包含 " + key + " 文件路径 end ++++++++++++++++++++\n\n")
        sb.toString()
    }
}