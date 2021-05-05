package com.mlg.obu.udp;

import android.util.Log;

import org.apache.commons.codec.CharEncoding;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPSocket {

    public static ExecutorService mThreadPool;
    private static final int POOL_SIZE = 5;
    // 单个CPU线程池大小
    private OnUDPReceiveCallbackBlock udpReceiveCallback;
    public static DatagramSocket ds;
    public static HeartbeatTimer timer;
    private Thread clientThread;
    private static UDPSocket udpsocket = null;
    private static final String BROADCAST_IP = "192.168.0.102";
    // 端口号
    public static final int CLIENT_PORT = 8080;
    private byte[] receiveData;
    private byte[] data;


    public static UDPSocket getInstance()  {
        //创建接收端的Socket服务对象，并且指定端口号
        if (ds == null) {
            try {
                ds = new DatagramSocket(CLIENT_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        int cpuNumbers = Runtime.getRuntime().availableProcessors();
        // 根据CPU数目初始化线程池
        mThreadPool = Executors.newFixedThreadPool(cpuNumbers * POOL_SIZE);
        // 记录创建对象时的时间
        if (udpsocket == null) {
            synchronized (UDPSocket.class) {
                if (udpsocket == null) {
                    udpsocket = new UDPSocket();

                }
            }
        }

        return udpsocket;
    }

    public void sendUdpV2X(int time, byte[] message) throws IOException {
        final byte[] requestData = new String(message, CharEncoding.ISO_8859_1)
                .getBytes(CharEncoding.ISO_8859_1);

//	    	时间发送频率。
        timer = new HeartbeatTimer();
        timer.setOnScheduleListener(new HeartbeatTimer.OnScheduleListener() {

            @Override
            public void onSchedule() {
                // TODO Auto-generated method stub
                    mThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InetAddress targetAddress =
                                        InetAddress.getByName(BROADCAST_IP);

                                DatagramPacket dp = new DatagramPacket(requestData,
                                        requestData.length, targetAddress, CLIENT_PORT);

                                ds.send(dp);

                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    System.out.println("发送成功 ： " + Arrays.toString(requestData) + "   bytes3.length()==" + requestData.length);
                }
        });

        timer.startTimer(0, time);

    }

    public void ShowData() {
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    //创建一个数据包，用于接收数据
                    byte[] bys = new byte[1024];
                    DatagramPacket dp = new DatagramPacket(bys, bys.length);
                    //接收数据
                    try {
                        ds.receive(dp);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //获取ip地址
                    String ip = dp.getAddress().getHostAddress();
                    data = dp.getData();
                    try {
                        receiveData = new String(data, CharEncoding.ISO_8859_1).getBytes(CharEncoding.ISO_8859_1);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    udpReceiveCallback.OnParserComplete(receiveData);
                    Log.e("aaaaasssss", "receiveMessage: " + "from ip :" + ip + " is " + "data :" + Arrays.toString(receiveData) + "长度= " + receiveData.length);
                }
            }
        });
        clientThread.start();

    }
    /*数据接收回调*/
    public interface OnUDPReceiveCallbackBlock {
        void OnParserComplete(byte[] data);
    }
    public void setUdpReceiveCallback(OnUDPReceiveCallbackBlock callback) {
        this.udpReceiveCallback = callback;
    }

    /*停止发送*/
    public void StopSocket(){
        if (timer != null) {
            timer.exit();
        }
    }

}
