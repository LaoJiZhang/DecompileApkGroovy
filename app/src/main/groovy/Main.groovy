import com.google.gson.Gson
import okhttp3.*
import rx.Observable
import rx.functions.Action1
import rx.functions.Func1

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Main {

    static String ORIGIN_FILE_PATH = "apk/"
    static String URL_FORMAT = "http://apis.wandoujia.com/five/v2/apps/tops/weeklytopapp?v=5.17.1&deviceId=ODY2NjU0MDI3MjYwNDAy&sdk=22&id=wandoujia_android&launchedCount=7&udid=9858a9f26eaa4c78bf7160f552f72a9e196b81fd&channel=wandoujia_pc_wandoujia2_homepage&rippleSupported=false&vc=12029&capacity=3&launchedAge=0&start=%d&max=%d"
    static int START_NUMBER = 60
    static int END_NUMBER = 4

    static int successfulCount = 0
    static ExecutorService sExecutorService = Executors.newSingleThreadExecutor()
    OkHttpClient mClient = new OkHttpClient()

    static void main(String[] args) {
        Main main = new Main()
        successfulCount = 0
        main.getDownloadInfo()
    }

    def getDownloadInfo = {
        String fileName = ORIGIN_FILE_PATH + "api_info_" + START_NUMBER + "_" + END_NUMBER + ".txt"
        String body = null
        File apiJsonInfoFile = new File(fileName)
        if (apiJsonInfoFile.exists() && apiJsonInfoFile.readBytes().length > 0) {
            body = new String(apiJsonInfoFile.readBytes())
//            Process p = ("open " + fileName).execute()
//            println "${p.text}"
        } else {
            String url = String.format(URL_FORMAT, START_NUMBER, END_NUMBER)
            println("downloadUrl = " + url)
            Request request = new Request.Builder()
                    .url(url)
                    .build()
            try {
                Response response = mClient.newCall(request).execute()
                body = response.body().string()
                apiJsonInfoFile.write(body)
            } catch (Exception e) {
                printlnMsg("豌豆荚Api请求错误信息：" + e.toString())
            }
        }

        if (body != null)
            analyseDownloadInfo.call(body)
    }

    def analyseDownloadInfo = { String body ->
        WanDouJiaResponse wanDouJiaResponse = new Gson().fromJson(body, WanDouJiaResponse.class)
        printlnMsg("本次共需要反编译 " + wanDouJiaResponse.getEntity().size() + " 个Apk")
        if (wanDouJiaResponse.getEntity().size() > 0) {
            Observable
                    .from(wanDouJiaResponse.getEntity())
                    .map(
                    new Func1<WanDouJiaEntity, List<WanDouJiaEntity.Apk>>() {
                        @Override
                        List<WanDouJiaEntity.Apk> call(WanDouJiaEntity wanDouJiaEntity) {
                            List<WanDouJiaEntity.Apk> apkList = wanDouJiaEntity.getDetail().getAppDetail().getApk()
                            return apkList
                        }
                    })
                    .flatMap(
                    new Func1<List<WanDouJiaEntity.Apk>, Observable<WanDouJiaEntity.Apk>>() {
                        @Override
                        Observable call(List<WanDouJiaEntity.Apk> apks) {
                            return Observable.from(apks)
                        }
                    })
                    .subscribe(
                    new Action1<WanDouJiaEntity.Apk>() {

                        @Override
                        void call(WanDouJiaEntity.Apk apk) {
                            downloadApk.call(apk)
                        }
                    },
                    new Action1<Throwable>() {
                        @Override
                        void call(Throwable throwable) {
                            printlnMsg("Observable 筛选单个Apk错误信息：" + throwable.toString())
                        }
                    }
            );
        }
    }

    def downloadApk = { WanDouJiaEntity.Apk apk ->
        String url = apk.getDownloadUrl().getUrl()

        String pathStr = ORIGIN_FILE_PATH
        File filePath = new File(pathStr)
        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        String apkName = pathStr + apk.getPackageName() + ".apk"
        File apkFile = new File(apkName)

        Request request = new Request.Builder().url(url).build()
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            void onFailure(Call call, IOException e) {
                String errorMsg = null
                if (apkFile.exists()) {
                    errorMsg = "delete fail apk:    " + apkFile.getAbsolutePath()
                    apkFile.deleteOnExit()
                }
                errorMsg = (errorMsg == null ? "" : errorMsg + "  ") + "download onFailure IOException: " + e.toString()
                printlnMsg(errorMsg)
            }

            @Override
            void onResponse(Call call, Response response) throws IOException {
                new Thread(new Runnable() {
                    @Override
                    void run() {
                        try {
                            printlnMsg("download " + apkName + " : " + Thread.currentThread())
                            InputStream inputStream = response.body().byteStream()
                            apkFile.write("")
                            apkFile.append(inputStream)
                            inputStream.close()
                            successfulCount++
                            printlnMsg("successfulCount = " + successfulCount + "      download Success: " + apkName + "      " + Thread.currentThread())
                            analyseSingleApk(apkFile)
                        } catch (Exception e1) {
                            printlnMsg("download onResponse Exception: " + e1.toString())
                        }
                    }
                }).start()
            }
        })
    }

    def analyseSingleApk = { File apkFile ->
        Runnable runnable = new Runnable() {
            @Override
            void run() {
                printlnMsg(apkFile.getName() + "    Decode start  " + Thread.currentThread())
                AnalyseApk.instance.decodeApk.call(apkFile)
            }
        }
        sExecutorService.execute(runnable)
    }

    static def printlnMsg = { String msg ->
        println("---------------------------------  " + msg + "  ---------------------------------")
    }
}