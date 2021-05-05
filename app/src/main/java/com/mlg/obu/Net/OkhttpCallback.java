package com.mlg.obu.Net;

import android.content.Context;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.mlg.obu.R;
import com.mlg.obu.Utils.LoadIngView;
import com.zhy.http.okhttp.callback.Callback;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Response;

public abstract class OkhttpCallback extends Callback<String> {

    private Context context;
    private LoadIngView loading;
    private Boolean ShowLoadIngView;

    public OkhttpCallback(Context context,Boolean showloadingview ) {
        if (showloadingview){
            loading = new LoadIngView(context, R.style.CustomDialogp);
            Window window = loading.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent);// 一句话搞定
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.dimAmount = 0.0f;
            window.setAttributes(params);
            loading.show();
        }
        this.context = context;
        this.ShowLoadIngView = showloadingview;
    }

    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        return response.body().string();
    }
    public void onError(Call call, final Exception e, final int id) {

        String msg = e.getMessage();
        if (e instanceof SocketTimeoutException) {
            msg = "您的网络开小差咯~";
        } else if (e instanceof UnknownHostException) {
            msg = "服务器繁忙，请稍后重试~";
        }
        else if(e instanceof ConnectException){
            msg = "您的网络开小差咯~";
        }
        else if(e instanceof Exception){
            msg = "服务器繁忙，请稍后重试~";
        }

        if(0 == id && msg.equals("request failed , reponse's code is : 500")){
            msg = "服务器繁忙，请稍后重试~";
        }

       // LogUtils.d(Tag, "onError code:" + id + "  msg:" + msg);

        final String finalMsg = msg;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onFaile(id, finalMsg, e,loading,ShowLoadIngView);
            }
        },2000);
    }

    @Override
    public void onResponse(final String  response, final int id) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onSuccess(response,response,id,loading,ShowLoadIngView);
            }
        },2000);
    }

    /**
     * 请求失败
     *
     * @param code 失败码
     * @param msg  失败信息
     * @param e    Callback 原始Exception
     */
    public abstract void onFaile(int code, String msg, Exception e, LoadIngView progressDialog,Boolean ShowLoadIngView);

    /**
     * 请求失败
     *  @param strData     data数据
     * @param response Callback原始response
     */
    public abstract void onSuccess(String  strData, String response, int id, LoadIngView progressDialog,Boolean ShowLoadIngView) ;

}
