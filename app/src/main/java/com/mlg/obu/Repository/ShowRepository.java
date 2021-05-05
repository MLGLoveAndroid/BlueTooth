package com.mlg.obu.Repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.mlg.obu.Base.BaseRepository;
import com.mlg.obu.Bean.TokenBean;
import com.mlg.obu.Net.Constants;
import com.mlg.obu.Net.OkhttpCallback;
import com.mlg.obu.Utils.LoadIngView;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;

public class ShowRepository extends BaseRepository {

    private Context context;
    private Boolean ShowLoadIngView;

    public ShowRepository(Context context,Boolean ShowLoadIngView) {
        this.context = context;
        this.ShowLoadIngView = ShowLoadIngView;
    }
    /*
     * Token
     * */

    public void getEveryData(String id,int type,String hashInfo,String timestamp,final HttpCallListener<TokenBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id",id);
        map.put("type",type+"");
        map.put("hashInfo",hashInfo);
        map.put("timestamp",timestamp);
        RequestCall requestCall = sendPust(Constants.BASEURL, Constants.auth, map);
        requestCall.execute(new OkhttpCallback(context, ShowLoadIngView) {
            @Override
            public void onFaile(int code, String msg, Exception e, LoadIngView progressDialog, Boolean ShowLoadIngView) {
                Log.e("aaaTAG", "onFaile: " +  code);

                if (ShowLoadIngView) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(String strData, String response, int id, LoadIngView progressDialog, Boolean ShowLoadIngView) {

                Log.e("aaaTAG", "onSuccess: " +  id);
                if (ShowLoadIngView){
                    progressDialog.dismiss();
                }
                TokenBean tokenBean = gson.fromJson(strData,new TypeToken<TokenBean>(){}.getType());
                if (listener != null){
                    listener.onHttpCallSuccess(tokenBean);
                }
            }
        });
    }
}
