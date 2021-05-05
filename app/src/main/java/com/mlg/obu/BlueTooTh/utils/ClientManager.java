package com.mlg.obu.BlueTooTh.utils;

import com.inuker.bluetooth.library.BluetoothClient;
import com.mlg.obu.Base.App;

/**
 * Created by dingjikerbo on 2016/8/27.
 */

public class ClientManager {

    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(App.getInstance());
                }
            }
        }
        return mClient;
    }
}
