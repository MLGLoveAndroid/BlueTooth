package com.mlg.obu.BlueTooTh;

import com.mlg.obu.udp.UDPSocket;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.Executors;

public class BlueToothCallback {
    public static BlueToothCallback blueToothCallback = null;
    public  OnblueToothReceiveCallbackBlock blueToothReceiveCallback;

    public static BlueToothCallback getInstance()  {

        if (blueToothCallback == null) {
            synchronized (BlueToothCallback.class) {
                if (blueToothCallback == null) {
                    blueToothCallback = new BlueToothCallback();
                }
            }
        }
        return blueToothCallback;
    }

    /*数据接收回调*/
    public interface OnblueToothReceiveCallbackBlock {
        void OnblueToothParserComplete(byte[] data);
    }
    public void setblueToothReceiveCallback(OnblueToothReceiveCallbackBlock callback) {
        this.blueToothReceiveCallback = callback;
    }

}
