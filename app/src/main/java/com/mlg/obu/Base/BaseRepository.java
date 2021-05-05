package com.mlg.obu.Base;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.mlg.obu.Net.MyOkhttpUtils;
import com.zhy.http.okhttp.BuildConfig;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

public class BaseRepository {

    protected Gson gson = new Gson();

    public interface HttpCallListener<T> {

         void onHttpCallFaile(int code, String msg);
         void onHttpCallSuccess(T data);

    }

    protected String map2json (HashMap<String ,String > map){
        return gson.toJson(map);
    }

    protected void loggerMap(String tag, HashMap<String, String> map){
        if (!BuildConfig.DEBUG)
            return ;

        if(map == null || map.size() == 0){
            LogUtils.json(tag);
            return;
        }

        LogUtils.json(tag);
    }
    /**
     * get请求
     *
     * @return
     */
    public RequestCall sendGet(String module, String action, Map<String, String> paramsMap) {
        return MyOkhttpUtils.sendGet(module, action, paramsMap);



    }
    public RequestCall sendPust(String module, String action, Map<String, String> paramsMap){
        return MyOkhttpUtils.sendPost(module, action, paramsMap);
    }

}
