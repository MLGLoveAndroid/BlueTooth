package com.mlg.obu.Base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.inuker.bluetooth.library.BluetoothContext;
import com.mlg.obu.Utils.IpGetUtil;


    public class App extends Application {
    private static final Handler sHandler = new Handler();
    private static Toast sToast; // 单例Toast,避免重复创建，显示时间过长
    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @SuppressLint("ShowToast")
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BluetoothContext.set(this);
        sToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        String localIpAddress = IpGetUtil.getIPAddress(this);
        Log.e("localIpAddress", "localIpAddress =  "+localIpAddress );
    }

    public static void toast(String txt, int duration) {
        sToast.setText(txt);
        sToast.setDuration(duration);
        sToast.show();
    }
    public static void showToast(String str) {
        sToast.setText(str);
        sToast.show();
    }

    public static void runUi(Runnable runnable) {
        sHandler.post(runnable);
    }


}
