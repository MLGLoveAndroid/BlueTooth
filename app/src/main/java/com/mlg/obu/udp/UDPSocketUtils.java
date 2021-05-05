package com.mlg.obu.udp;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by melo on 2017/9/20.
 */

public class UDPSocketUtils {

    private static final String TAG = "UDPSocket";

    // 单个CPU线程池大小
    private static final int POOL_SIZE = 5;

    private static final int BUFFER_LENGTH = 1024;
    private String string;
    private byte[] receiveByte = new byte[BUFFER_LENGTH];

    private static final String BROADCAST_IP = Constants.SOCKET_HOST;

    // 端口号，飞鸽协议默认端口2425
    public static final int CLIENT_PORT = Constants.SOCKET_UDP_PORT;

    private boolean isThreadRunning = false;

    private Context mContext;
    private DatagramSocket client;
    private DatagramPacket receivePacket;

    private long lastReceiveTime = 0;
    private int time ;
    private static final long TIME_OUT = 120 * 1000;
    private static final long HEARTBEAT_MESSAGE_DURATION = 1000;

    private ExecutorService mThreadPool;
    private Thread clientThread;
    private HeartbeatTimer timer;
    //private Users localUser;
    //private Users remoteUser;
    private OnUDPReceiveCallbackBlock udpReceiveCallback;

    public UDPSocketUtils(Context context) {

        this.mContext = context;
      /*  this.string = s;*/

        int cpuNumbers = Runtime.getRuntime().availableProcessors();
        // 根据CPU数目初始化线程池
        mThreadPool = Executors.newFixedThreadPool(cpuNumbers * POOL_SIZE);
        // 记录创建对象时的时间
        lastReceiveTime = System.currentTimeMillis();

    }

  /*  *//**
     * 创建本地用户信息
     *//*
    private void createUser() {
        if (localUser == null) {
            localUser = new Users();
        }
        if (remoteUser == null) {
            remoteUser = new Users();
        }

        localUser.setImei(DeviceUtil.getDeviceId(mContext));
        localUser.setSoftVersion(DeviceUtil.getPackageVersionCode(mContext));

        if (WifiUtil.getInstance(mContext).isWifiApEnabled()) {// 判断当前是否是开启热点方
            localUser.setIp("192.168.43.1");
        } else {// 当前是开启 wifi 方
            localUser.setIp(WifiUtil.getInstance(mContext).getLocalIPAddress());
            remoteUser.setIp(WifiUtil.getInstance(mContext).getServerIPAddress());
        }
    }*/


    public void startUDPSocket(String Data,int time) {
        this.string = Data;
        this.time = time;
        if (client != null) return;
        try {
            // 表明这个 Socket 在设置的端口上监听数据。
            client = new DatagramSocket(CLIENT_PORT);

            if (receivePacket == null) {
                // 创建接受数据的 packet
                receivePacket = new DatagramPacket(receiveByte, BUFFER_LENGTH);
            }
            startSocketThread();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启发送数据的线程
     */
    private void startSocketThread() {
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
           //     Log.d(TAG, "clientThread is running...");
                receiveMessage();
            }
        });
        isThreadRunning = true;
        clientThread.start();

      // if (!IpGetUtil.getIPAddress(mContext).equals("192.168.0.100")){
           startHeartbeatTimer();
      // }
    }

    /**
     * 处理接受到的消息
     */
    private void receiveMessage() {

        Log.d(TAG, "aaaaaareceive packet success...");
        Log.d(TAG, "aaaaaareceive packet success..."+isThreadRunning);

        while (isThreadRunning) {

            try {
                Log.d(TAG, "ee packet success..."+isThreadRunning);

                if (client != null) {
                    Log.d(TAG, "ddd packet success..."+isThreadRunning);
                 //   client.setSoTimeout(2000);
                    client.receive(receivePacket);
                    Log.d(TAG, "vvvv packet success..."+isThreadRunning);

                }
                lastReceiveTime = System.currentTimeMillis();
                Log.d(TAG, "receive packet success...");
            } catch (IOException e) {
                Log.e(TAG, "UDP数据包接收失败！线程停止");
                stopUDPSocket();
                e.printStackTrace();
                return;
            }
            Log.d(TAG, "aaaaaareceive packet success..."+isThreadRunning);

            if (receivePacket == null || receivePacket.getLength() == 0) {
                Log.e(TAG, "无法接收UDP数据或者接收到的UDP数据为空");
                continue;
            }
            Log.d(TAG, "aaaaaareceive packet success..."+isThreadRunning);

            String strReceive = new String(receivePacket.getData(), 0, receivePacket.getLength());
            Log.d(TAG, strReceive + " from " + receivePacket.getAddress().getHostAddress() + ":" + receivePacket.getPort());

          /*  // 每次接收完UDP数据后，重置长度。否则可能会导致下次收到数据包被截断。
            if (receivePacket != null) {
                receivePacket.setLength(BUFFER_LENGTH);
            }*/
            //解析接收到的 json 信息
            if (udpReceiveCallback != null) {
                udpReceiveCallback.OnParserComplete(receivePacket);
            }
        }
    }
    //{name:mlg,phone:17610630717}
    public void stopUDPSocket() {
        isThreadRunning = false;
        receivePacket = null;
        if (clientThread != null) {
            clientThread.interrupt();
        }
        if (client != null) {
            client.close();
            client = null;
        }
        if (timer != null) {
            timer.exit();
        }
    }

    /**
     * 启动心跳，timer 间隔十秒
     */
    private void startHeartbeatTimer() {

       // sendMessage(string);

       timer = new HeartbeatTimer();
        timer.setOnScheduleListener(new HeartbeatTimer.OnScheduleListener() {
            @Override
            public void onSchedule() {
               // Log.d(TAG, "timer is onSchedule...");
                long duration = System.currentTimeMillis() - lastReceiveTime;
                //Log.d(TAG, "duration:" + duration);
                if (duration > TIME_OUT) {//若超过两分钟都没收到我的心跳包，则认为对方不在线。
                //    Log.d(TAG, "超时，对方已经下线");
                    // 刷新时间，重新进入下一个心跳周期
                    lastReceiveTime = System.currentTimeMillis();
                } else if (duration > HEARTBEAT_MESSAGE_DURATION) {//若超过十秒他没收到我的心跳包，则重新发一个。
                    sendMessage(string);
                }
            }

        });
        timer.startTimer(0, time);
    }

    /**
     * 发送心跳包
     *
     * @param message
     */
    public void sendMessage(final String message) {




        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress targetAddress = InetAddress.getByName(BROADCAST_IP);

                    DatagramPacket packet = new DatagramPacket(message.getBytes("UTF-8"), message.length(), targetAddress, CLIENT_PORT);

                    client.send(packet);

                    // 数据发送事件
                //    Log.d(TAG, "数据发送成功" + message.length());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public interface OnUDPReceiveCallbackBlock {
        void OnParserComplete(DatagramPacket data);
    }
    public void setUdpReceiveCallback(OnUDPReceiveCallbackBlock callback) {
        this.udpReceiveCallback = callback;
    }
}

