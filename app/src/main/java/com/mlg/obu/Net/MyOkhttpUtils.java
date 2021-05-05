package com.mlg.obu.Net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public  class MyOkhttpUtils {
private static MyOkhttpUtils myOkhttpUtils;
    public static final String MEDIATYPE_APPLICATION_JSON = "" +
            "application/json; charset=utf-8";
    //    private static CookieJarImpl cookieJar;
    private static String Token = "token";
    private static Map<String, String> header;

    public static void initHttp() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
//                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//添加SSL加密
                    .addInterceptor(new LoggerInterceptor("TAG"))//添加Log
                    .build();
            OkHttpUtils.initClient(okHttpClient);
        } catch (Exception e) {
            //LogUtils.d(e.getMessage());
        }

    }

    private static Map<String, String> getHeader() {
        String token = "";
          header = new HashMap<>();
         if (null == token || TextUtils.isEmpty(token)) {
            header.put("Content-Type","application/json;charset=utf-8");
            header.put("api-key","");
            return header;

        }else {

                 header.put("Content-Type","application/json;charset=utf-8");
             header.put("api-key","");
             //header.put(Token, token);
             return header;
        }

    }

    /**
     * get请求
     *
     * @return
     */
    public static RequestCall sendGet(String module, String action, Map<String, String> paramsMap) {

        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils.get()
                .url(url)
                .headers(getHeader())
                .params(xxt(paramsMap))
                .build();
        Log.e("url",url);
        return call;
    }

    /**
     * get请求
     *
     * @return
     */
    public static RequestCall sendGetTag(String tag, String module, String action, Map<String, String> paramsMap) {
        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils.get()
                .url(url)
                .headers(getHeader())
                .params(xxt(paramsMap))
                .tag(tag)
                .build();


        return call;
    }

    /**
     * get请求
     *
     * @return
     */
    public static RequestCall sendGet(String baseUrl, String module, String action, Map<String, String> paramsMap) {
        String url = getUrl(baseUrl, module, action);
        RequestCall call = OkHttpUtils.get()
                .url(url)
                .headers(getHeader())
                .params(xxt(paramsMap))
                .build();
        return call;
    }

    /**
     * get请求
     *
     * @return
     */
    public static RequestCall sendGetTag(String tag, String baseUrl, String module, String action, Map<String, String> paramsMap) {
        String url = getUrl(baseUrl, module, action);
        RequestCall call = OkHttpUtils.get()
                .url(url)
                .headers(getHeader())
                .params(xxt(paramsMap))
                .tag(tag)
                .build();
        return call;
    }

    /**
     * Put请求
     *
     * @return
     */
    public static RequestCall sendJsonPut(String module, String action, String jsonParams) {
        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils.put()
                .url(url)
                .headers(getHeader())
                .requestBody(RequestBody.create(MediaType.parse(MEDIATYPE_APPLICATION_JSON), jsonParams))
                .build();
        return call;
    }


    /**
     * Put请求
     *
     * @return
     */
    public static RequestCall sendJsonPutTag(String tag, String module, String action, String jsonParams) {
        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils.put()
                .url(url)
                .headers(getHeader())
                .requestBody(RequestBody.create(MediaType.parse(MEDIATYPE_APPLICATION_JSON), jsonParams))
                .build();
        return call;
    }

    protected Gson gson = new Gson();

    protected String map2json(Map<String, String> map) {
        return gson.toJson(map);
    }

    /**
     * XXT 加密
     */
    private static Map<String, String> xxt(Map<String, String> paramsMap) {
        return paramsMap;

//        HashMap<String, String> map = new HashMap<>();
//        map.put(Constants.XXT_Parameter, XXTEA.encryptToBase64String(map2json(paramsMap), Constants.XXT_Key));
//
//        return map;
    }

    /**
     * 直播创建  post
     */

    /**
     * post请求
     *
     * @return
     */
    public static RequestCall sendPost(String module, String action, Map<String, String> paramsMap) {
        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils
                .post()
                .url(url)
                .headers(getHeader())
                .params(xxt(paramsMap))
                .build();
        return call;
    }

    /**
     * post请求
     *
     * @return
     */
    public static RequestCall sendPostTag(String tag, String module, String action, Map<String, String> paramsMap) {
        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils
                .post()
                .url(url)
                .headers(getHeader())
                .params(xxt(paramsMap))
                .tag(tag)
                .build();
        return call;
    }


    /**
     * post请求
     *
     * @return
     */
    public static RequestCall sendPostWithHost(String url, Map<String, String> paramsMap) {
        RequestCall call = OkHttpUtils
                .post()
                .url(url)
                .params(paramsMap)
                .build();
        return call;
    }

    /**
     * post请求
     *
     * @return
     */
    public static RequestCall sendPostWithHostTag(String tag, String url, Map<String, String> paramsMap) {
        RequestCall call = OkHttpUtils
                .post()
                .headers(getHeader())
                .tag(tag)
                .url(url)
                .params(paramsMap)
                .build();
        return call;
    }


    /**
     * postString请求
     *
     * @return
     */
    public static RequestCall sendStringPost(String module, String action, String jsonParams) {
        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils
                .postString()
                .url(url)
                .headers(getHeader())
                .content(jsonParams)
                .build();
        return call;
    }

    /**
     * postString请求
     *
     * @return
     */
    public static RequestCall sendStringPostTag(String tag, String module, String action, String jsonParams) {
        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils
                .postString()
                .url(url)
                .tag(tag)
                .headers(getHeader())
                .content(jsonParams)
                .build();
        return call;
    }

    /**
     * Post JSON请求
     *
     * @return
     */
    public static RequestCall sendPostJson(String module, String action, String jsonParams) {

        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils
                .postString()
                .url(url)
                .headers(getHeader())
                .content(jsonParams)
                .mediaType(MediaType.parse(MEDIATYPE_APPLICATION_JSON))
                .build();


        return call;
    }
    /*
     *      POST    不需要传惨  测试
     * */
    public static RequestCall sendPostVip(String module){
        String url = getUrl(module);
        RequestCall call = OkHttpUtils
                .post()
                .url(url)
                .build();
        return call;
    }

    /**
     * Post JSON请求
     *
     * @return
     */
    public static RequestCall sendPostJsonTag(String tag, String module, String action, String jsonParams) {

        String url = getUrl(module, action);
        RequestCall call = OkHttpUtils
                .postString()
                .url(url)
                .tag(tag)
                .headers(getHeader())
                .content(jsonParams)
                .mediaType(MediaType.parse(MEDIATYPE_APPLICATION_JSON))
                .build();

        return call;
    }

    /**
     * 下载App请求方法
     *
     * @param callBack
     */
    public static void downNewApp(String url, FileCallBack callBack) {
        OkHttpUtils.get()
                .url(url)
                .headers(getHeader())
                .build().execute(callBack);
    }

    /**
     * 下载文件请求方法
     *
     * @param callBack
     */
    public static void sendDownGetTag(String tag, String module, String action, Map<String, String> paramsMap, FileCallBack callBack) {
        String url = getUrl(module, action);
        OkHttpUtils.get()
                .url(url)
                .tag(tag)
                .headers(getHeader())
                .params(paramsMap)
                .build().execute(callBack);
    }


    /**
     * 上传文件请求
     *
     * @param callBack
     */
    public static void postFile(String module, String action, String name, String filename, File file, StringCallback callBack) {
        String url = getUrl(module, action);
        OkHttpUtils.post()
                .url(url)
                .headers(getHeader())
                .addFile(name, filename, file)
                .build().execute(callBack);
    }

    //创建直播  上传封面图
    public static RequestCall createPostFile(String module, String action, Map<String,String>map){
        String url = getUrl(module, action);
        RequestCall build = OkHttpUtils.post()
                .url(url)
                .params(map)
                .headers(getHeader())
                .build();
        return build;
    }


    /**
     * 上传文件请求
     *
     * @param callBack
     */
    public static void postFileTag(String tag, String module, String action, String name, String filename, File file, StringCallback callBack) {
        String url = getUrl(module, action);
        OkHttpUtils.post()
                .url(url)
                .headers(getHeader())
                .addFile(name, filename, file)
                .build().execute(callBack);
    }



    /*//测试WebSocke
public static String getWebUrl(String module,String action){
    String url = Constants.WebHOST + "/" + module + "/" + action;
    return url;
}*/
    public static String getUrl(String module, String action) {
        String url = Constants.HOST + "/" + module + "/" + action;
        return url;
    }

    public static String getUrl(String baseUrl, String module, String action) {
        String url = baseUrl + "/" + module + "/" + action;
        return url;
    }

    //会员升级  网络    请求
    public static String getUrl(String module) {
        String url = Constants.HOST + "/" + module;
        return url;
    }


}
